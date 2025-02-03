package beyond.momentours.pre_blacklist.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PreBlackListDTO {

    private Long preBlackId;
    private int blackListCount;
    private String status;
    private LocalDateTime createdAt;
    private Long memberId;
    private Long reportId;
}
