package beyond.momentours.course_scrap.command.domain.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseCreateCourseScrapVO {
    private Long courseScrapId;
    private Long courseScrapFolderId;
    private Long courseId;
    private LocalDateTime createdAt;
}
