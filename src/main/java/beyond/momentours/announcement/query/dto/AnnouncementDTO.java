package beyond.momentours.announcement.query.dto;


import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AnnouncementDTO {

    private Long announcementId;
    private String announcementTitle;
    private String announcementContent;
    private Boolean announcementStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long memberId;


}
