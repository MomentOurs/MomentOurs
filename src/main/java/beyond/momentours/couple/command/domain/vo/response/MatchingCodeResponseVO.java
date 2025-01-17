package beyond.momentours.couple.command.domain.vo.response;

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
public class MatchingCodeResponseVO {
    private String id;
    private Long memberId;
    private LocalDateTime createdAt;
    private MatchingStatus matchingStatus;
}
