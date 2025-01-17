package beyond.momentours.couple.command.application.mapper;

import beyond.momentours.couple.command.application.dto.MatchingCodeDTO;
import beyond.momentours.couple.command.domain.aggregate.entity.MatchingCode;
import beyond.momentours.couple.command.domain.vo.response.MatchingCodeResponseVO;
import org.springframework.stereotype.Component;

@Component
public class CodeConverter {
    public MatchingCodeDTO fromCodeToDto(MatchingCode code) {
        return MatchingCodeDTO.builder()
                .id(code.getId())
                .memberId(code.getMemberId())
                .createdAt(code.getCreatedAt())
                .matchingStatus(code.getMatchingStatus())
                .build();
    }

    public MatchingCodeResponseVO fromDtoToResponseVO(MatchingCodeDTO dto) {
        return MatchingCodeResponseVO.builder()
                .id(dto.getId())
                .memberId(dto.getMemberId())
                .createdAt(dto.getCreatedAt())
                .matchingStatus(dto.getMatchingStatus())
                .build();
    }
}
