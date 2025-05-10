package beyond.momentours.date_course.command.domain.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestUpdateDateCourseScheduleVO {
    private LocalDateTime courseStartDate;
    private LocalDateTime courseEndDate;
    private String planType;
}
