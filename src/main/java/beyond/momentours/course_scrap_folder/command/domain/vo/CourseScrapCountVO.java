package beyond.momentours.course_scrap_folder.command.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseScrapCountVO {
    private Long folderId;
    private int courseCount;
}
