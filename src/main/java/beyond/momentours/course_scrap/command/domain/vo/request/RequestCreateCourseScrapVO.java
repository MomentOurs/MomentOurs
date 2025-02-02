package beyond.momentours.course_scrap.command.domain.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCreateCourseScrapVO {
    private Long courseScrapFolderId;
    private Long courseId;
}
