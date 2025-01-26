package beyond.momentours.location.command.application.mapper;

import beyond.momentours.location.command.application.dto.LocationDTO;
import beyond.momentours.location.command.domain.aggregate.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationConverter {
    public LocationDTO fromEntityToDTO(Location savedLocation) {
        return LocationDTO.builder()
                .locationId(savedLocation.getLocationId())
                .locationName(savedLocation.getLocationName())
                .latitude(savedLocation.getLatitude())
                .longitude(savedLocation.getLongitude())
                .locationStatus(savedLocation.getLocationStatus())
                .build();
    }
}
