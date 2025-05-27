package beyond.momentours.location.query.service;

import beyond.momentours.location.command.application.dto.LocationDTO;
import beyond.momentours.location.query.vo.*;

import java.math.BigDecimal;
import java.util.List;

public interface LocationQueryService {
    LocationDTO getLocationById(Long locationId);

    List<ResponseLocationMapVO> getLocationsInBounds(BigDecimal latitudeMin, BigDecimal latitudeMax, BigDecimal longitudeMin, BigDecimal longitudeMax);

    List<ResponseLocationClusterItemVO> getClusteredLocations(BigDecimal lat, BigDecimal lng);

    ResponseLocationMomentPageVO getLocationWithMoments(Long locationId, Long cursor, int size);

    List<ResponseLocationClusterGroupVO> getGroupedLocationClustersByZoom(int zoom);

    List<ResponseLocationSearchVO> searchLocation(String keyword);

    List<ResponseLocationMapVO> findByGeoHashPrefix(BigDecimal latitude, BigDecimal longitude, int zoom);


    List<ResponseLocationMapVO> findNearby(BigDecimal latitude, BigDecimal longitude, int radiusMeters);
}
