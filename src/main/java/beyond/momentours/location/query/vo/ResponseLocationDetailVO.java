package beyond.momentours.location.query.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ResponseLocationDetailVO {
    private Long locationId;
    private String locationName;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private long momentCount;
    private long totalView;
    private long totalLike;
}
