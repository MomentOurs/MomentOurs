package beyond.momentours.course_scrap.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseScrapDTO {
    private Long courseScrapId;
    private Long courseScrapFolderId;
    private Long courseId;
    private LocalDateTime createdAt;
}
