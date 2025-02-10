package beyond.momentours.member.query.vo.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseLoginHistoryVO {
    private Long loginHistoryId;
    private LocalDateTime loginHistoryDate;
    private String loginHistoryIp;
    private Long memberId;
}
