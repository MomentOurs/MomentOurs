package beyond.momentours.moment.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.location.command.application.dto.LocationDTO;
import beyond.momentours.location.command.application.service.LocationCommandService;
import beyond.momentours.location.query.service.LocationQueryService;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.moment.command.application.dto.MomentDTO;
import beyond.momentours.moment.common.converter.MomentConverter;
import beyond.momentours.moment.command.domain.aggregate.entity.Moment;
import beyond.momentours.moment.command.domain.aggregate.repository.MomentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MomentCommandServiceImpl implements MomentCommandService {

    private final MomentRepository momentRepository;
    private final LocationCommandService locationCommandService;
    private final LocationQueryService locationQueryService;
    private final MomentConverter momentConverter;

    @Transactional
    @Override
    public MomentDTO createMoment(MomentDTO dto, Long memberId) {
        LocationDTO location;

        if (dto.getLocationId() != null) {
            location = locationQueryService.getLocationById(dto.getLocationId());
        } else if (dto.getLocationName() != null && dto.getLatitude() != null && dto.getLongitude() != null) {
            location = locationCommandService.findOrCreateLocation(dto.getLocationName(), dto.getLatitude(), dto.getLongitude());
        } else {
            throw new CommonException(ErrorCode.INVALID_LOCATION_DATA);
        }

        Moment moment = momentConverter.fromDTOToEntity(dto, memberId, location.getLocationId());
        moment.createMoment();
        Moment saved = momentRepository.save(moment);
        return momentConverter.fromEntityToDTO(saved);
    }

    @Transactional
    @Override
    public MomentDTO updateMoment(MomentDTO dto, CustomUserDetails user) {
        Moment moment = momentRepository.findById(dto.getMomentId()).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_MOMENT));

        if (!moment.getMemberId().equals(user.getMember().getMemberId())) throw new CommonException(ErrorCode.UNAUTHORIZED_ACCESS);

        moment.updateMoment(dto);
        Moment updated = momentRepository.save(moment);
        return momentConverter.fromEntityToDTO(updated);
    }
}
