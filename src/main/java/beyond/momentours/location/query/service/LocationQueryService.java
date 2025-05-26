package beyond.momentours.location.query.service;

import beyond.momentours.location.command.application.dto.LocationDTO;
import beyond.momentours.location.query.vo.ResponseLocationMapVO;

import java.math.BigDecimal;
import java.util.List;

public interface LocationQueryService {
    LocationDTO getLocationById(Long locationId);

    List<ResponseLocationMapVO> getLocationsInBounds(BigDecimal latitudeMin, BigDecimal latitudeMax, BigDecimal longitudeMin, BigDecimal longitudeMax);
}
