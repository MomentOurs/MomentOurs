package beyond.momentours.location.query.service;

import beyond.momentours.location.command.application.dto.LocationDTO;
import beyond.momentours.location.query.vo.ResponseLocationClusterGroupVO;
import beyond.momentours.location.query.vo.ResponseLocationClusterItemVO;
import beyond.momentours.location.query.vo.ResponseLocationMapVO;
import beyond.momentours.location.query.vo.ResponseLocationMomentPageVO;

import java.math.BigDecimal;
import java.util.List;

public interface LocationQueryService {
    LocationDTO getLocationById(Long locationId);

    List<ResponseLocationMapVO> getLocationsInBounds(BigDecimal latitudeMin, BigDecimal latitudeMax, BigDecimal longitudeMin, BigDecimal longitudeMax);

    List<ResponseLocationClusterItemVO> getClusteredLocations(BigDecimal lat, BigDecimal lng);

    ResponseLocationMomentPageVO getLocationWithMoments(Long locationId, Long cursor, int size);

    List<ResponseLocationClusterGroupVO> getGroupedLocationClustersByZoom(int zoom);
}
