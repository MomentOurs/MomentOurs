package beyond.momentours.couplelog.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CoupleLogDTO {
    private Long coupleLogId;
    private Long memberId;
    private Long coupleId;
    private String text;
    private Boolean coupleLogStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
