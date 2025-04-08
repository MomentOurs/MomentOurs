package beyond.momentours.course_scrap_folder.command.domain.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCreateCourseScrapFolderVO {
    private String folderName;
    private String folderDescription;
    private MultipartFile folderImage;
}
