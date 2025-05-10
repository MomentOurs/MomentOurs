package beyond.momentours.date_course.command.domain.vo.request;

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
public class RequestCreateDateCourseVO {
    private String courseTitle;
    private CourseType courseType;
    private Boolean courseDisclosure;
    private LocalDateTime courseStartDate;
    private LocalDateTime courseEndDate;
    private Long folderId;

//    private List<DateCourseLocationVO> locations;
}
