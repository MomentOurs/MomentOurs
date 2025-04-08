package beyond.momentours.course_scrap_folder.command.domain.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCreateCourseScrapFolderVO {
    private Long courseScrapFolderId;
    private String folderName;
    private String folderDescription;
    private String folderImage;
    private Long memberId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

