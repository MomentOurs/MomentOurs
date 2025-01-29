package beyond.momentours.course_scrap_folder.command.domain.vo.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RequestCreateCourseScrapFolderVO {
    private String folderName;
}
