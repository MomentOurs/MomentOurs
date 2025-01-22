package beyond.momentours.couplelog.command.application.service;

import beyond.momentours.couplelog.command.application.dto.CoupleLogDTO;
import beyond.momentours.couplelog.command.domain.vo.request.CoupleLogRequestVO;
import beyond.momentours.couplelog.command.domain.vo.response.EditSessionResponse;
import beyond.momentours.member.command.application.dto.CustomUserDetails;

public interface CommandCoupleLogService {
    void createNewCoupleLog(CoupleLogRequestVO requestVO, CustomUserDetails user);

    CoupleLogDTO updateCoupleLog(Long couplelogId, Long memberId, CoupleLogRequestVO requestVO);
}
