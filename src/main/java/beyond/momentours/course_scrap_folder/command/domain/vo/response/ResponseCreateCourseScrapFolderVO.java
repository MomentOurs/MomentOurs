package beyond.momentours.course_scrap_folder.command.domain.vo.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ResponseCreateCourseScrapFolderVO {
    private Long folderId;
    private String folderName;
    private Long memberId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
