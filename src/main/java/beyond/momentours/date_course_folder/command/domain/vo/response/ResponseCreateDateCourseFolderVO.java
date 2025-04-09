package beyond.momentours.date_course_folder.command.domain.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCreateDateCourseFolderVO {
    private Long folderId;
    private String folderName;
    private String folderDescription;
}
