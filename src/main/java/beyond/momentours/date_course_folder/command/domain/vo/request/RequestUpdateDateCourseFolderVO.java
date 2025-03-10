package beyond.momentours.date_course_folder.command.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestUpdateDateCourseFolderVO {

    @NotBlank(message = "폴더 이름은 필수 입력값입니다.")
    @JsonProperty("folder_name")
    private String folderName;
}
