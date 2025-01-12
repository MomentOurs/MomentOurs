package beyond.momentours.moment.command.application.service;

import beyond.momentours.location.command.domain.aggregate.entity.Location;
import beyond.momentours.location.command.domain.repository.LocationRepository;
import beyond.momentours.moment.command.application.dto.RequestMomentDTO;
import beyond.momentours.moment.command.application.dto.ResponseMomentDTO;
import beyond.momentours.moment.command.domain.aggregate.entity.Moment;
import beyond.momentours.moment.command.domain.repository.MomentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service("commandMomentService")
public class MomentServiceImpl implements MomentService {

    private final MomentRepository momentRepository;
    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MomentServiceImpl(MomentRepository momentRepository,
                             LocationRepository locationRepository,
                             ModelMapper modelMapper) {
        this.momentRepository = momentRepository;
        this.locationRepository = locationRepository;
        this.modelMapper = modelMapper;
    }

    // 추억 등록
    @Transactional
    @Override
    public ResponseMomentDTO createMoment(RequestMomentDTO requestMomentDTO) {

        // 추억 등록 시 참조하는 장소가 DB에 존재하는 장소인지 확인
        Location location = locationRepository.findByLatitudeAndLongitude(
                requestMomentDTO.getLatitude(),
                requestMomentDTO.getLongitude()
        ).orElseGet(() -> { // 존재하지 않으면, 장소 테이블에 새로운 장소 삽입
            Location newLocation = modelMapper.map(requestMomentDTO, Location.class);
            return locationRepository.save(newLocation);
        });

        Moment moment = modelMapper.map(requestMomentDTO, Moment.class);
        moment.setLocationId(location.getLocationId());
        moment.setCreatedAt(LocalDateTime.now());
        moment.setUpdatedAt(LocalDateTime.now());

        Moment savedMoment = momentRepository.save(moment);
        return modelMapper.map(savedMoment, ResponseMomentDTO.class);
    }
}
