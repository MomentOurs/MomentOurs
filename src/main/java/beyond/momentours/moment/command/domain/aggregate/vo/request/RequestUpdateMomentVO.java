package beyond.momentours.moment.command.domain.aggregate.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestUpdateMomentVO {
    private Long momentId;
    private String momentTitle;
    private String momentCategory;
    private String momentContent;
    private String momentImageUrls;
    private boolean momentCommentStatus;
}
