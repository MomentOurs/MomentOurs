package beyond.momentours.location.query.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.location.command.application.dto.LocationDTO;
import beyond.momentours.location.command.application.mapper.LocationConverter;
import beyond.momentours.location.command.domain.aggregate.entity.Location;
import beyond.momentours.location.query.repository.LocationMapper;
import beyond.momentours.location.query.vo.ResponseLocationClusterGroupVO;
import beyond.momentours.location.query.vo.ResponseLocationClusterItemVO;
import beyond.momentours.location.query.vo.ResponseLocationMapVO;
import beyond.momentours.location.query.vo.ResponseLocationMomentPageVO;
import beyond.momentours.moment.query.repository.MomentMapper;
import beyond.momentours.moment.query.vo.ResponseMomentCursorListVO;
import beyond.momentours.moment.query.vo.ResponseMomentListItemVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationQueryServiceImpl implements LocationQueryService {

    private final LocationMapper locationMapper;
    private final LocationConverter locationConverter;
    private final MomentMapper momentMapper;

    @Override
    public LocationDTO getLocationById(Long locationId) {
        Location location = locationMapper.findById(locationId);

        if (location == null) {
            log.warn("존재하지 않는 장소 ID 조회 요청: {}", locationId);
            throw new CommonException(ErrorCode.NOT_FOUND_LOCATION);
        }

        return locationConverter.fromEntityToDTO(location);
    }

    @Override
    public List<ResponseLocationMapVO> getLocationsInBounds(BigDecimal latitudeMin, BigDecimal latitudeMax, BigDecimal longitudeMin, BigDecimal longitudeMax) {
        return locationMapper.findLocationsInBounds(latitudeMin, latitudeMax, longitudeMin, longitudeMax);
    }

    @Override
    public List<ResponseLocationClusterItemVO> getClusteredLocations(BigDecimal latitude, BigDecimal longitude) {
        return locationMapper.findLocationsNearCoordinates(latitude, longitude);
    }

    @Override
    public ResponseLocationMomentPageVO getLocationWithMoments(Long locationId, Long cursor, int size) {
        Location location = locationMapper.findById(locationId);
        if (location == null) throw new CommonException(ErrorCode.NOT_FOUND_LOCATION);

        List<ResponseMomentListItemVO> moments = momentMapper.findMomentsByLocationIdWithCursor(locationId, cursor, size);

        Long nextCursor = moments.isEmpty() ? null : moments.get(moments.size() - 1).getMomentId();
        boolean hasNext = moments.size() == size;

        return ResponseLocationMomentPageVO.builder()
                .locationId(location.getLocationId())
                .locationName(location.getLocationName())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .address(location.getAddress())
                .momentPage(ResponseMomentCursorListVO.builder()
                        .moments(moments)
                        .nextCursor(nextCursor)
                        .hasNext(hasNext)
                        .build())
                .build();
    }

    public List<ResponseLocationClusterGroupVO> getGroupedLocationClustersByZoom(int zoom) {
        int round;
        if (zoom >= 16) round = 4;
        else if (zoom >= 14) round = 3;
        else if (zoom >= 12) round = 2;
        else round = 1;

        return locationMapper.findGroupedLocationClusters(round);
    }

}
