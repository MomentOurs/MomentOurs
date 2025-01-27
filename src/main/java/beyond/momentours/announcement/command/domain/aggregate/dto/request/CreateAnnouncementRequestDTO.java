package beyond.momentours.announcement.command.domain.aggregate.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateAnnouncementRequestDTO {

    // 공지사항 등록 요청 DTO
    @JsonProperty("announcement_title")
    private String announcementTitle;

    @JsonProperty("announcement_content")
    private String announcementContent;


}


