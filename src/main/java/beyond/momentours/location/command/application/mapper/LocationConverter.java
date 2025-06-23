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
                .address(savedLocation.getAddress())
                .imageUrl(savedLocation.getImageUrls())
                .latitude(savedLocation.getLatitude())
                .longitude(savedLocation.getLongitude())
                .locationStatus(savedLocation.getLocationStatus())
                .description(savedLocation.getDescription())
                .rating(savedLocation.getRating())
                .isOpen(savedLocation.getIsOpen())
                .closingTime(savedLocation.getClosingTime())
                .build();
    }
}
