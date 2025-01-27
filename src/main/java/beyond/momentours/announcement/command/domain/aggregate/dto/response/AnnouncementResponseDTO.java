package beyond.momentours.announcement.command.domain.aggregate.dto.response;


import beyond.momentours.announcement.command.domain.aggregate.entity.Announcement;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnnouncementResponseDTO {

    // 응답 정보
    @JsonProperty("announcement_id")
    private Long announcementId;

    @JsonProperty("announcement_title")
    private String announcementTitle;

    @JsonProperty("announcement_content")
    private String announcementContent;

    @JsonProperty("announcement_status")
    private Boolean announcementStatus;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("member_id")
    private Long memberId;

    // 엔티티를 DTO로 변환하는 정적 메서드
    public static AnnouncementResponseDTO fromEntity(Announcement announcement) {
        return new AnnouncementResponseDTO(
                announcement.getAnnouncementId(),
                announcement.getAnnouncementTitle(),
                announcement.getAnnouncementContent(),
                announcement.getAnnouncementStatus(),
                announcement.getCreatedAt(),
                announcement.getUpdatedAt(),
                announcement.getMemberId()
        );
    }


}
