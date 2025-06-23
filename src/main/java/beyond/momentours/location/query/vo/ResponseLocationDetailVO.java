package beyond.momentours.location.query.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ResponseLocationDetailVO {
    private Long locationId;
    private String locationName;
    private String address;
    private String images;
    private String description;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private long momentCount;
    private long totalView;
    private long totalLike;

    public List<String> getImages() {
        return images == null ? List.of() : List.of(images.split(","));
    }
}
