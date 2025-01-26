package beyond.momentours.location.command.application.dto;

import beyond.momentours.location.command.domain.aggregate.LocationStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class LocationDTO {
    private Long locationId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String locationName;
    private LocationStatus locationStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
