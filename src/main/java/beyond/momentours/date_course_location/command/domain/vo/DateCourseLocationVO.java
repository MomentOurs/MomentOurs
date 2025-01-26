package beyond.momentours.date_course_location.command.domain.vo;

import lombok.*;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DateCourseLocationVO {
    private String locationName;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer sequence;
}