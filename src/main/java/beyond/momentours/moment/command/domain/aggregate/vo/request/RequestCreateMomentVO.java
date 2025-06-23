package beyond.momentours.moment.command.domain.aggregate.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestCreateMomentVO {
    private String momentTitle;
    private String momentCategory;
    private String momentContent;
    private boolean momentCommentStatus;
    private Long momentLike;
    private Long momentView;
    private String momentImageUrls;
    private Long locationId;

    // 처음 추억이 등록되는 경우에는 네이버 지도 API를 통해 정보 반환
    private String locationName;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
