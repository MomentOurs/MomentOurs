package beyond.momentours.announcement.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Table(name = "tb_announcement")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id")
    private Long announcementId;

    @Column(name = "announcement_title", nullable = false)
    private String announcementTitle;

    @Column(name = "announcement_content", nullable = false)
    private String announcementContent;

    @Column(name = "announcement_status", nullable = false)
    private Boolean announcementStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

}
