package beyond.momentours.blacklist.command.application.mapper;

import beyond.momentours.blacklist.command.application.dto.BlackListDTO;
import beyond.momentours.blacklist.command.domain.aggregate.entity.BlackList;
import beyond.momentours.blacklist.command.domain.vo.request.RequestBlackVO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BlackListConverter {

    public BlackListDTO fromBlackVOToBlackDTO(RequestBlackVO requestBlackVO) {
        return BlackListDTO.builder()
                .memberId(requestBlackVO.getMemberId())
                .blacklistDays(requestBlackVO.getBlacklistDays())
                .build();
    }

    public BlackList fromBlackDTOToBlackList(LocalDateTime currentDate, LocalDateTime accessibleDate, Long memberId) {
        return BlackList.builder()
                .memberId(memberId)
                .blackDate(currentDate)
                .accessibleDate(accessibleDate)
                .blackStatus(true)
                .build();
    }
}
