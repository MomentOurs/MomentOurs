package beyond.momentours.location.command.application.service;

import beyond.momentours.location.command.application.dto.LocationDTO;
import beyond.momentours.location.command.application.mapper.LocationConverter;
import beyond.momentours.location.command.domain.aggregate.LocationStatus;
import beyond.momentours.location.command.domain.aggregate.entity.Location;
import beyond.momentours.location.command.domain.repository.LocationRepository;
import beyond.momentours.location.query.repository.LocationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@Service("commandLocationService")
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationConverter locationConverter;
    private final LocationMapper locationDAO;

    @Override
    public LocationDTO findOrCreateLocation(String locationName, BigDecimal latitude, BigDecimal longitude) {
        Location existingLocation = locationDAO.findByLatitudeAndLongitudeAndLocationName(latitude, longitude, locationName);

        if (existingLocation != null) {
            log.info("기존 장소를 반환 : {}", existingLocation);
            return locationConverter.fromEntityToDTO(existingLocation);
        }

        log.info("새로운 장소를 생성 : 상호명={}, 위도={}, 경도={}", locationName, latitude, longitude);
        Location newLocation = createLocation(locationName, latitude, longitude);

        Location savedLocation = locationRepository.save(newLocation);
        return locationConverter.fromEntityToDTO(savedLocation);
    }

    private Location createLocation(String locationName, BigDecimal latitude, BigDecimal longitude) {
        ZonedDateTime nowKST = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        return Location.builder()
                .locationName(locationName)
                .latitude(latitude)
                .longitude(longitude)
                .locationStatus(LocationStatus.UNCHANGED)
                .createdAt(nowKST.toLocalDateTime())
                .updatedAt(nowKST.toLocalDateTime())
                .build();
    }
}
