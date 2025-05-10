package beyond.momentours.date_course_location.command.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DateCourseLocationVO {
    private Long locationId;
    private String locationName;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer sequence;
    private String courseMemo;
}