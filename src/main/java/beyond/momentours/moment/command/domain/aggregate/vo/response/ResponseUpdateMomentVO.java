package beyond.momentours.moment.command.domain.aggregate.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUpdateMomentVO {
    private Long momentId;
    private String momentTitle;
    private String momentImageUrls;
    private LocalDateTime updatedAt;
}
