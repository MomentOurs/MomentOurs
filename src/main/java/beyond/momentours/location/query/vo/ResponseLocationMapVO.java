package beyond.momentours.location.query.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ResponseLocationMapVO {
    private Long locationId;
    private String locationName;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer momentCount;
    private String description;

    @JsonIgnore
    private String images;

    public List<String> getImages() {
        if (images == null || images.isEmpty()) return List.of();
        return List.of(images.split(","));
    }
}
