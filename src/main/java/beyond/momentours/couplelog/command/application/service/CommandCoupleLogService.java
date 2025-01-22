package beyond.momentours.couplelog.command.application.service;

import beyond.momentours.couplelog.command.domain.vo.request.RequestCoupleLogVO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;

public interface CommandCoupleLogService {
    void createNewCoupleLog(RequestCoupleLogVO requestVO, CustomUserDetails user);
}
