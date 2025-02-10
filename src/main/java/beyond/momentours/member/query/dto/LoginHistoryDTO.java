package beyond.momentours.member.query.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LoginHistoryDTO {
    private Long loginHistoryId;
    private LocalDateTime loginHistoryDate;
    private String loginHistoryIp;
    private Long memberId;

    private MemberDTO member;
}
