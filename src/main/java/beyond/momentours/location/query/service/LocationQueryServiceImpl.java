package beyond.momentours.location.query.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.location.command.application.dto.LocationDTO;
import beyond.momentours.location.command.application.mapper.LocationConverter;
import beyond.momentours.location.command.application.service.LocationCommandService;
import beyond.momentours.location.command.domain.aggregate.entity.Location;
import beyond.momentours.location.common.util.LocationFilterUtil;
import beyond.momentours.location.common.util.LocationGeoUtil;
import beyond.momentours.location.common.util.LocationTextUtil;
import beyond.momentours.location.query.external.NaverMapSearchClient;
import beyond.momentours.location.query.external.dto.NaverPlaceDTO;
import beyond.momentours.location.query.repository.LocationMapper;
import beyond.momentours.location.query.vo.*;
import beyond.momentours.moment.common.MomentFilterCondition;
import beyond.momentours.moment.query.repository.MomentMapper;
import beyond.momentours.moment.query.vo.ResponseMomentCursorListVO;
import beyond.momentours.moment.query.vo.ResponseMomentListItemVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
    public ResponseLocationMomentPageVO getLocationWithMoments(MomentFilterCondition condition) {
        Location location = locationMapper.findById(condition.getLocationId());
        if (location == null) throw new CommonException(ErrorCode.NOT_FOUND_LOCATION);

        ResponseMomentCursorListVO ours = getCursorPagedMoments(condition, true);
        ResponseMomentCursorListVO others = getCursorPagedMoments(condition, false);

        return ResponseLocationMomentPageVO.builder()
                .locationId(location.getLocationId())
                .locationName(location.getLocationName())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .address(location.getAddress())
                .description(location.getDescription())
                .ours(ours)
                .others(others)
                .build();
    }

    private ResponseMomentCursorListVO getCursorPagedMoments(MomentFilterCondition baseCondition, boolean ours) {
        MomentFilterCondition condition = baseCondition.toBuilder().isOurs(ours).build();
        List<ResponseMomentListItemVO> list = getMomentsByLocationIdWithFilter(condition);
        Long nextCursor = list.isEmpty() ? null : list.get(list.size() - 1).getMomentId();
        boolean hasNext = list.size() == condition.getSize();

        return ResponseMomentCursorListVO.builder()
                .moments(list)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }

    private List<ResponseMomentListItemVO> getMomentsByLocationIdWithFilter(MomentFilterCondition condition) {
        return momentMapper.findMomentsByLocationIdWithFilter(
                condition.getLocationId(),
                condition.getCursor(),
                condition.getSize(),
                condition.getSort(),
                condition.getIsOurs(),
                condition.getCertifiedOnly(),
                condition.getMemberId(),
                condition.getCoupleId()
        );
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
        String normalized = LocationTextUtil.normalizeKeyword(keyword);

        List<ResponseLocationSearchVO> results = locationMapper.findLocationByKeyword(normalized);

        if (results.isEmpty()) {
            List<NaverPlaceDTO> externalPlaces = naverMapSearchClient.searchPlaces(keyword);

            List<ResponseLocationSearchVO> valid = new ArrayList<>();
            for (NaverPlaceDTO dto : externalPlaces) {
                String category = dto.getCategory();
                if (category == null || LocationFilterUtil.isExcludedPlace(category)) continue;

                valid.add(ResponseLocationSearchVO.builder()
                        .locationId(null)
                        .locationName(dto.getTitle())
                        .latitude(dto.getLatitude())
                        .longitude(dto.getLongitude())
                        .address(dto.getAddress())
                        .description(dto.getDescription())
                        .build());
            }
            return valid;
        }

        List<ResponseLocationSearchVO> top3 = results.subList(0, Math.min(3, results.size()));

        List<ResponseLocationSearchVO> remaining = new ArrayList<>();
        if (results.size() > 3) {
            remaining.addAll(results.subList(3, results.size()));
            Collections.shuffle(remaining);
        }

        List<ResponseLocationSearchVO> random7 = new ArrayList<>();
        if (!remaining.isEmpty()) {
            random7 = remaining.subList(0, Math.min(7, remaining.size()));
        }

        List<ResponseLocationSearchVO> finalList = new ArrayList<>();
        finalList.addAll(top3);
        finalList.addAll(random7);

        return finalList;
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

    @Override
    public List<ResponseLocationMapVO> findRecommendedNearby(BigDecimal latitude, BigDecimal longitude, int radiusMeters, int limit) {
        return locationMapper.findRecommendedNearbyLocations(latitude, longitude, radiusMeters, limit);
    }

    @Override
    public ResponseLocationDetailVO getLocationDetail(Long locationId) {
        ResponseLocationDetailVO result = locationMapper.getLocationDetail(locationId);
        if (result == null) throw new CommonException(ErrorCode.NOT_FOUND_LOCATION);
        return result;
    }

    @Override
    public List<ResponseLocationClusterItemVO> getClusteredLocationsInBounds(
            BigDecimal latitude, BigDecimal longitude, int zoom,
            BigDecimal latitudeMin, BigDecimal latitudeMax,
            BigDecimal longitudeMin, BigDecimal longitudeMax
    ) {
        int latRound = getRoundingScale(zoom);
        int lngRound = getRoundingScale(zoom);

        if (zoom < 13) {
            return locationMapper.findClusteredLocationsByZoomLevelWithLimit(
                    latRound, lngRound,
                    latitudeMin, latitudeMax,
                    longitudeMin, longitudeMax,
                    30
            );
        } else {
            return locationMapper.findClusteredLocationsByZoomLevel(
                    latRound, lngRound,
                    latitudeMin, latitudeMax,
                    longitudeMin, longitudeMax
            );
        }
    }

    @Override
    public List<ResponseLocationMapVO> getClusterLocations(BigDecimal latitude, BigDecimal longitude, int zoom) {
        int round = getRoundingScale(zoom);
        return locationMapper.findLocationsByRoundedCoordinate(latitude, longitude, round);
    }

    private int getRoundingScale(int zoom) {
        if (zoom >= 16) return 4;
        else if (zoom >= 14) return 3;
        else if (zoom >= 12) return 2;
        else if (zoom >= 10) return 1;
        else return 0;
    }
}
