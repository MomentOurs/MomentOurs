package beyond.momentours.course_scrap.command.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseScrapVO {
    private Long courseScrapId;
    private Long courseScrapFolderId;
    private Long courseId;
    private LocalDateTime createdAt;
}
