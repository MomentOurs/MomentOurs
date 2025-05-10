package beyond.momentours.course_scrap_folder.command.domain.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseCourseScrapFolderVO {
    private Long courseScrapFolderId;
    private String folderName;
    private String folderDescription;
    private String folderImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int courseCount;
}
