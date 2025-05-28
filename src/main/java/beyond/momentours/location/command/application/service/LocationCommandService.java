package beyond.momentours.location.command.application.service;

import beyond.momentours.location.command.application.dto.LocationDTO;

import java.math.BigDecimal;

public interface LocationCommandService {
    LocationDTO findOrCreateLocation(String locationName, BigDecimal latitude, BigDecimal longitude);

    LocationDTO createLocationWithAddress(String locationName, BigDecimal latitude, BigDecimal longitude, String address);
}
