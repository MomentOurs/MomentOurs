package beyond.momentours.location.query.service;

import beyond.momentours.location.command.application.dto.LocationDTO;

public interface LocationQueryService {
    LocationDTO getLocationById(Long locationId);
}
