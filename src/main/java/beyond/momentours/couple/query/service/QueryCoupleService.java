package beyond.momentours.couple.query.service;

import beyond.momentours.couple.command.application.dto.CoupleListDTO;

import java.util.List;

public interface QueryCoupleService {
    CoupleListDTO getCoupleByCoupleId(Long coupleId);

    CoupleListDTO getCoupleByMemberId(Long memberId);

    Long getCoupleIdByMemberId(Long memberId);

    List<Long> getAllCoupleIds();

    List<Long> getMemberIdsByCoupleId(Long coupleId);
}
