package beyond.momentours.blacklist.query.dto;

import beyond.momentours.member.query.dto.MemberDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BlacklistDTO {

    private Long memberId;
    private Boolean blackStatus;
    private LocalDateTime blackDate;
    private LocalDateTime accessibleDate;
    private Integer blacklistDays;

    private MemberDTO member;
}
