package beyond.momentours.course_scrap_folder.command.domain.vo.response;

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
public class ResponseCourseScrapFolderVO {
    private Long courseScrapFolderId;
    private String folderName;
    private String folderImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
