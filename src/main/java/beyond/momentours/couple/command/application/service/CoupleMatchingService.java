package beyond.momentours.couple.command.application.service;

import beyond.momentours.couple.command.application.dto.CoupleListDTO;
import beyond.momentours.couple.command.application.dto.MatchingCodeDTO;

public interface CoupleMatchingService {
    MatchingCodeDTO createMatchingCode(Long userId);
    CoupleListDTO authenticationMatchingCode(String matchingCode, Long memberId);
}
