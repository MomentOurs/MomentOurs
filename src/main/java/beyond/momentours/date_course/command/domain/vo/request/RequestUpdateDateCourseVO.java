package beyond.momentours.date_course.command.domain.vo.request;

import beyond.momentours.date_course.command.domain.aggregate.CourseType;
import beyond.momentours.date_course_location.command.domain.vo.DateCourseLocationVO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestUpdateDateCourseVO {
    private String courseTitle;
    private CourseType courseType;
    private String courseMemo;
    private Boolean courseDisclosure;
    private LocalDateTime courseStartDate;
    private LocalDateTime courseEndDate;

    private List<DateCourseLocationVO> locations;
}
