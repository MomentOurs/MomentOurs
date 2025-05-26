package beyond.momentours.location.query.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.location.command.application.dto.LocationDTO;
import beyond.momentours.location.command.application.mapper.LocationConverter;
import beyond.momentours.location.command.domain.aggregate.entity.Location;
import beyond.momentours.location.query.repository.LocationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationQueryServiceImpl implements LocationQueryService {

    private final LocationMapper locationMapper;
    private final LocationConverter locationConverter;

    @Override
    public LocationDTO getLocationById(Long locationId) {
        Location location = locationMapper.findById(locationId);

        if (location == null) {
            log.warn("존재하지 않는 장소 ID 조회 요청: {}", locationId);
            throw new CommonException(ErrorCode.NOT_FOUND_LOCATION);
        }

        return locationConverter.fromEntityToDTO(location);
    }
}
