package beyond.momentours.location.command.application.service;

import beyond.momentours.location.command.application.dto.LocationDTO;

import java.math.BigDecimal;

public interface LocationService {
    LocationDTO findOrCreateLocation(String locationName, BigDecimal latitude, BigDecimal longitude);
}
