package beyond.momentours.couple.command.domain.vo;

import beyond.momentours.couple.command.domain.aggregate.entity.MatchingStatus;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class MatchingCodeVO {
    private String id;
    private Long memberId;
    private LocalDateTime createdAt;
    private MatchingStatus matchingStatus;
}
