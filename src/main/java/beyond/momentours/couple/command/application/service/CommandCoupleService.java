package beyond.momentours.couple.command.application.service;

import beyond.momentours.couple.command.application.dto.CoupleProfileDTO;
import beyond.momentours.couple.command.domain.vo.request.CoupleProfileRequestVO;

public interface CommandCoupleService {

    CoupleProfileDTO updateProfile(CoupleProfileRequestVO requestVO);
}
