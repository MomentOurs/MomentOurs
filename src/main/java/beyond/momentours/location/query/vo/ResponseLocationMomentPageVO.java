package beyond.momentours.location.query.vo;

import beyond.momentours.moment.query.vo.ResponseMomentCursorListVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class ResponseLocationMomentPageVO {
    private Long locationId;
    private String locationName;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String address;
    private ResponseMomentCursorListVO momentPage;
}
