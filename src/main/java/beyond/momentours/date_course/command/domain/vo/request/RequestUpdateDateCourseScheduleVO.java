package beyond.momentours.date_course.command.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RequestUpdateDateCourseScheduleVO {
    private LocalDateTime courseStartDate;
    private LocalDateTime courseEndDate;
    private String planType;
}
