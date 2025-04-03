package beyond.momentours.date_course.command.domain.vo.response;

import beyond.momentours.date_course.command.domain.aggregate.CourseType;
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