package beyond.momentours.moment.command.application.service;

import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.moment.command.application.dto.RequestMomentDTO;
import beyond.momentours.moment.command.application.dto.ResponseMomentDTO;

public interface MomentService {
    ResponseMomentDTO createMoment(RequestMomentDTO requestMomentDTO, Long memberId);

    ResponseMomentDTO updateMoment(Long momentId, RequestMomentDTO requestMomentDTO, Long memberId);

    ResponseMomentDTO deleteMoment(Long momentId, Long memberId);
}
