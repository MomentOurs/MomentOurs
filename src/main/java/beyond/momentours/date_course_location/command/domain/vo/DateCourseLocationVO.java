package beyond.momentours.date_course_location.command.domain.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DateCourseLocationVO {
    private Long locationId;
    private String locationName;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer sequence;
    private String courseMemo;
}