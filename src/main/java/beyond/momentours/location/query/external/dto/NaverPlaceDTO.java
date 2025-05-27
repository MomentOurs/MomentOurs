package beyond.momentours.location.query.external.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class NaverPlaceDTO {
    private String title;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String category;
}
