package beyond.momentours.date_course_folder.command.domain.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestCreateDateCourseFolderVO {
    private String folderName;
    private String folderDescription;
}
