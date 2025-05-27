package beyond.momentours.moment.query.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ResponseMomentDetailVO {
    private Long momentId;
    private String momentTitle;
    private String momentCategory;
    private String momentContent;
    private Boolean momentDisclosure;
    private Boolean momentCommentStatus;
    private Long momentLike;
    private Long momentView;
    private String locationName;
    private String address;
    private String memberNickname;
    private LocalDateTime createdAt;
}
