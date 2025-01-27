package beyond.momentours.date_course.command.application.dto;

import beyond.momentours.date_course.command.domain.aggregate.CourseType;
import beyond.momentours.date_course_location.command.domain.vo.DateCourseLocationVO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DateCourseDTO {
    private Long courseId;
    private String courseTitle;
    private CourseType courseType;
    private String courseMemo;
    private Boolean courseDisclosure;
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
