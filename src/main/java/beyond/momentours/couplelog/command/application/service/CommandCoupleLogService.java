package beyond.momentours.couplelog.command.application.service;

import beyond.momentours.couplelog.command.domain.vo.request.CoupleLogRequestVO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;

public interface CommandCoupleLogService {
    void createNewCoupleLog(CoupleLogRequestVO requestVO, CustomUserDetails user);
}
