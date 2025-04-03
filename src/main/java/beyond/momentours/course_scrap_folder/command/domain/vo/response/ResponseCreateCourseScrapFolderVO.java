package beyond.momentours.course_scrap_folder.command.domain.vo.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResponseCreateCourseScrapFolderVO {
    private Long courseScrapFolderId;
    private String folderName;
    private String folderDescription;
    private String folderImage;
    private Long memberId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

