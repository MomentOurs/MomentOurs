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
public class ResponseMomentDetailVO {
    private Long momentId;
    private String momentTitle;
    private String momentCategory;
    private String momentContent;
    private Boolean momentCertified;
    private Boolean momentCommentStatus;
    private Long momentLike;
    private Long momentView;
    private Long commentCount;
    private String locationName;
    private String address;
    private String momentImageUrl;
    private String memberNickname;
    private String coupleName;
    private String couplePhoto;
    private LocalDateTime createdAt;
}
