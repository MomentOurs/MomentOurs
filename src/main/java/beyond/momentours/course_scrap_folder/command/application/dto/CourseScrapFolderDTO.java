package beyond.momentours.course_scrap_folder.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CourseScrapFolderDTO {
    private Long courseScrapFolderId;
    private String folderName;
    private String folderDescription;
    private String folderImage;
    private Long memberId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}