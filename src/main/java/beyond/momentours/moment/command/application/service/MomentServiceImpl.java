package beyond.momentours.moment.command.application.service;

import beyond.momentours.location.command.application.dto.LocationDTO;
import beyond.momentours.location.command.application.service.LocationService;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.moment.command.application.dto.RequestMomentDTO;
import beyond.momentours.moment.command.application.dto.ResponseMomentDTO;
import beyond.momentours.moment.command.application.mapper.MomentConverter;
import beyond.momentours.moment.command.domain.aggregate.entity.Moment;
import beyond.momentours.moment.command.domain.repository.MomentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("commandMomentService")
@RequiredArgsConstructor
public class MomentServiceImpl implements MomentService {

    private final MomentRepository momentRepository;
    private final LocationService locationService;
    private final MomentConverter momentConverter;


    // 추억 등록
    @Transactional
    @Override
    public ResponseMomentDTO createMoment(RequestMomentDTO requestMomentDTO,
                                          Long memberId) {

        // 장소 조회 또는 생성
        LocationDTO location = locationService.findOrCreateLocation(
                requestMomentDTO.getLocationName(),
                requestMomentDTO.getLatitude(),
                requestMomentDTO.getLongitude()
        );

        // DTO → Entity 변환
        Moment moment = momentConverter.fromDTOToEntity(requestMomentDTO, memberId, location.getLocationId());

        // DB 저장
        Moment savedMoment = momentRepository.save(moment);

        // Entity → ResponseDTO 변환 후 반환
        return momentConverter.fromEntityToDTO(savedMoment);
    }

    @Override
    public ResponseMomentDTO updateMoment(RequestMomentDTO requestMomentDTO, CustomUserDetails user) {
        return null;
    }
}
