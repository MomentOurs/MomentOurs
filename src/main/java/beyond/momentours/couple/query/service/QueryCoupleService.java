package beyond.momentours.couple.query.service;

import beyond.momentours.couple.command.application.dto.CoupleListDTO;

public interface QueryCoupleService {
    CoupleListDTO getCoupleByCoupleId(Long coupleId);

    CoupleListDTO getCoupleByMemberId(Long memberId);
}
