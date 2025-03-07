package beyond.momentours.date_course_folder.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DateCourseFolderDTO {
    private Long folderId;
    private String folderName;
    private Long memberId;
}
