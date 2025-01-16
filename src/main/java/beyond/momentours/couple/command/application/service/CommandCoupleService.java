package beyond.momentours.couple.command.application.service;

import beyond.momentours.couple.command.application.dto.CoupleProfileDTO;
import beyond.momentours.couple.command.domain.vo.CoupleProfileRequestVO;

public interface CommandCoupleService {

    CoupleProfileDTO editProfile(CoupleProfileRequestVO requestVO);
}
