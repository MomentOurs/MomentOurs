package beyond.momentours.couple.query.service;

import beyond.momentours.couple.command.application.dto.CoupleListDTO;

public interface QueryCoupleService {
    CoupleListDTO getCoupleById(Long coupleId);
}
