package beyond.momentours.date_course.command.domain.vo.request;

import beyond.momentours.date_course.command.domain.aggregate.CourseType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestCreateDateCourseVO {
    private String courseTitle;
    private CourseType courseType;
    private String courseMemo;
    private LocalDateTime courseStartDate;
    private LocalDateTime courseEndDate;
}
