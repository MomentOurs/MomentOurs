package beyond.momentours.location.query.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.location.command.application.dto.LocationDTO;
import beyond.momentours.location.command.application.mapper.LocationConverter;
import beyond.momentours.location.command.application.service.LocationCommandService;
import beyond.momentours.location.command.domain.aggregate.entity.Location;
import beyond.momentours.location.common.util.LocationFilterUtil;
import beyond.momentours.location.common.util.LocationGeoUtil;
import beyond.momentours.location.query.external.NaverMapSearchClient;
import beyond.momentours.location.query.external.dto.NaverPlaceDTO;
import beyond.momentours.location.query.repository.LocationMapper;
import beyond.momentours.location.query.vo.*;
import beyond.momentours.moment.query.repository.MomentMapper;
import beyond.momentours.moment.query.vo.ResponseMomentCursorListVO;
import beyond.momentours.moment.query.vo.ResponseMomentListItemVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationQueryServiceImpl implements LocationQueryService {

    private final LocationMapper locationMapper;
    private final LocationConverter locationConverter;
    private final MomentMapper momentMapper;
    private final LocationCommandService locationCommandService;
    private final NaverMapSearchClient naverMapSearchClient;

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

    @Override
    public List<ResponseLocationClusterGroupVO> getGroupedLocationClustersByZoom(int zoom) {
        int round;
        if (zoom >= 16) round = 4;
        else if (zoom >= 14) round = 3;
        else if (zoom >= 12) round = 2;
        else round = 1;

        return locationMapper.findGroupedLocationClusters(round);
    }

    @Override
    public List<ResponseLocationSearchVO> searchLocation(String keyword) {
        String normalized = keyword.replaceAll("\\s+", "").toLowerCase();

        List<ResponseLocationSearchVO> results = locationMapper.findLocationByKeyword(normalized);

        if (results.isEmpty()) {
            List<NaverPlaceDTO> externalPlaces = naverMapSearchClient.searchPlaces(keyword);

            List<ResponseLocationSearchVO> valid = new ArrayList<>();
            for (NaverPlaceDTO dto : externalPlaces) {
                String category = dto.getCategory();
                if (category == null || LocationFilterUtil.isExcludedPlace(category)) continue;

                LocationDTO saved = locationCommandService.createLocationWithAddress(
                        dto.getTitle(), dto.getLatitude(), dto.getLongitude(), dto.getAddress()
                );

                valid.add(ResponseLocationSearchVO.builder()
                        .locationId(saved.getLocationId())
                        .locationName(saved.getLocationName())
                        .latitude(saved.getLatitude())
                        .longitude(saved.getLongitude())
                        .address(saved.getAddress())
                        .build());
            }
            return valid;
        }

        return results;
    }

    @Override
    public List<ResponseLocationMapVO> findByGeoHashPrefix(BigDecimal latitude, BigDecimal longitude, int zoom) {
        String fullGeoHash = LocationGeoUtil.generateGeoHash(latitude, longitude, 8);

        int prefixLength = determinePrefixLength(zoom);
        String prefix = fullGeoHash.substring(0, prefixLength);

        return locationMapper.findByGeoHashPrefix(prefix);
    }

    private int determinePrefixLength(int zoom) {
        if (zoom >= 16) return 8;
        else if (zoom >= 14) return 7;
        else if (zoom >= 12) return 6;
        else if (zoom >= 10) return 5;
        else return 4;
    }

    @Override
    public List<ResponseLocationMapVO> findNearby(BigDecimal latitude, BigDecimal longitude, int radiusMeters) {
        return locationMapper.findNearbyLocations(latitude, longitude, radiusMeters);
    }
}
