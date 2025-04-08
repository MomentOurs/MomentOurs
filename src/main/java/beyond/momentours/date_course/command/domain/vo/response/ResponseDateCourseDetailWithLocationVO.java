package beyond.momentours.date_course.command.domain.vo.response;

import beyond.momentours.date_course.command.domain.aggregate.CourseType;
import beyond.momentours.date_course_location.command.domain.vo.DateCourseLocationVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDateCourseDetailWithLocationVO {
    private Long courseId;
    private String courseTitle;
    private CourseType courseType;
    private Long courseLike;
    private Long courseView;
    private Boolean courseStatus;
    private LocalDateTime courseStartDate;
    private LocalDateTime courseEndDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long memberId;

    private List<DateCourseLocationVO> locations;
}
