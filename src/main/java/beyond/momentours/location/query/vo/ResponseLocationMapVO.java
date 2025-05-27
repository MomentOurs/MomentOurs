package beyond.momentours.location.query.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class ResponseLocationMapVO {
    private Long locationId;
    private String locationName;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer momentCount;
}
