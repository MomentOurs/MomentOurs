package beyond.momentours.moment.query.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMomentListItemVO {
    private Long momentId;
    private String momentTitle;
    private String momentCategory;
    private String locationName;
    private String momentImageUrl;
    private String momentContent;
    private boolean isOurs;
    private boolean commentEnabled;
    private Long likeCount;
    private Long viewCount;
    private LocalDateTime createdAt;
}
