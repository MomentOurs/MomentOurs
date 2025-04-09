package beyond.momentours.course_scrap_folder.command.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolderWithCourseIdsVO {
    private Long courseScrapFolderId;
    private String folderName;
    private String folderDescription;
    private String folderImageUrl;
    private List<Long> courseIds;
}
