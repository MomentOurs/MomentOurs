package beyond.momentours.couple.command.application.dto;

import beyond.momentours.couple.command.domain.aggregate.entity.MatchingStatus;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MatchingCodeDTO {
    private String id;
    private Long memberId;
    private LocalDateTime createdAt;
    private MatchingStatus matchingStatus;
}
