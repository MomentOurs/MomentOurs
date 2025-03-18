package beyond.momentours.date_course_folder.command.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseCreateDateCourseFolderVO {
    @JsonProperty("folder_id")
    private Long folderId;
    @JsonProperty("folder_name")
    private String folderName;
}
