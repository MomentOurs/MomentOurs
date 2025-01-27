package beyond.momentours.date_course.command.domain.vo.response;

import beyond.momentours.date_course.command.domain.aggregate.CourseType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseUpdateDateCourseVO {
    private Long courseId;
    private String courseTitle;
    private CourseType courseType;
    private String courseMemo;
    private Boolean courseDisclosure;
    private LocalDateTime courseStartDate;
    private LocalDateTime courseEndDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long memberId;
}
