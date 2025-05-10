package beyond.momentours.date_course.command.domain.vo.response;

import beyond.momentours.date_course.command.domain.aggregate.CourseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDateCourseListVO {
    private Long courseId;
    private String courseTitle;
    private CourseType courseType;
    private Long courseLike;
    private Long courseView;
    private LocalDateTime courseStartDate;
    private LocalDateTime courseEndDate;
    private Long memberId;
}