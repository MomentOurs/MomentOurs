package beyond.momentours.moment.command.application.service;

import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.moment.command.application.dto.MomentDTO;

public interface MomentCommandService {
    MomentDTO createMoment(MomentDTO momentDTO, Long memberId);

    MomentDTO updateMoment(MomentDTO momentDTO, CustomUserDetails user);
}
