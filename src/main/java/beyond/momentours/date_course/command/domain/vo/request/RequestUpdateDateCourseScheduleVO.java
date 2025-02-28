package beyond.momentours.date_course.command.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestUpdateDateCourseScheduleVO {
    @JsonProperty("course_start_date")
    private LocalDateTime courseStartDate;

    @JsonProperty("course_end_date")
    private LocalDateTime courseEndDate;

    @JsonProperty("plan_type")
    private String planType;
}
