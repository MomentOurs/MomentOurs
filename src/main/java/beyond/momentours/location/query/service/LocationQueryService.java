package beyond.momentours.location.query.service;

import beyond.momentours.location.command.application.dto.LocationDTO;
import beyond.momentours.location.query.vo.*;
import beyond.momentours.moment.common.MomentFilterCondition;

import java.math.BigDecimal;
import java.util.List;

public interface LocationQueryService {
    LocationDTO getLocationById(Long locationId);

    List<ResponseLocationMapVO> getLocationsInBounds(BigDecimal latitudeMin, BigDecimal latitudeMax, BigDecimal longitudeMin, BigDecimal longitudeMax);

    ResponseLocationMomentPageVO getLocationWithMoments(MomentFilterCondition condition);

    List<ResponseLocationClusterGroupVO> getGroupedLocationClustersByZoom(int zoom);

    List<ResponseLocationSearchVO> searchLocation(String keyword);

    List<ResponseLocationMapVO> findByGeoHashPrefix(BigDecimal latitude, BigDecimal longitude, int zoom);

    List<ResponseLocationMapVO> findNearby(BigDecimal latitude, BigDecimal longitude, int radiusMeters);

    List<ResponseLocationMapVO> findRecommendedNearby(BigDecimal latitude, BigDecimal longitude, int radiusMeters, int limit);

    ResponseLocationDetailVO getLocationDetail(Long locationId);

    List<ResponseLocationClusterItemVO> getClusteredLocationsInBounds(BigDecimal latitude, BigDecimal longitude, int zoom, BigDecimal latitudeMin, BigDecimal latitudeMax, BigDecimal longitudeMin, BigDecimal longitudeMax);

    List<ResponseLocationMapVO> getClusterLocations(BigDecimal latitude, BigDecimal longitude, int zoom);
}
