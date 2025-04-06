package beyond.momentours.date_course.command.domain.vo.request;

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
public class RequestCreateDateCourseVO {
    private String courseTitle;
    private CourseType courseType;
    private Boolean courseDisclosure;
    private LocalDateTime courseStartDate;
    private LocalDateTime courseEndDate;
    private Long folderId;

//    private List<DateCourseLocationVO> locations;
}
