package beyond.momentours.announcement.query.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncementDTO {

    private Long announcementId;
    private String announcementTitle;
    private String announcementContent;
    private Boolean announcementStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long memberId;


}
