package beyond.momentours.moment.command.domain.aggregate.entity;

import beyond.momentours.moment.command.application.dto.MomentDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_moment")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Builder
public class Moment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "moment_id")
    private Long momentId;

    @Column(name = "moment_title", nullable = false)
    private String momentTitle;

    @Column(name = "moment_category", nullable = false)
    private String momentCategory;

    @Column(name = "moment_content", nullable = false)
    private String momentContent;

    @Column(name = "moment_certified")
    private boolean momentCertified;

    @Column(name = "moment_comment_status")
    private boolean momentCommentStatus;

    @Column(name = "moment_like")
    private Long momentLike;

    @Column(name = "moment_view")
    private Long momentView;

    @Column(name = "moment_status", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private boolean momentStatus;

    @Column(name = "moment_image_urls", nullable = false)
    private String momentImageUrls;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "couple_id")
    private Long coupleId;

    public void createMoment() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.momentStatus = true;
        this.momentLike = 0L;
        this.momentView = 0L;
    }

    public void updateMoment(MomentDTO dto) {
        this.momentTitle = dto.getMomentTitle();
        this.momentCategory = dto.getMomentCategory();
        this.momentContent = dto.getMomentContent();
        this.momentCertified = dto.isMomentCertified();
        this.momentCommentStatus = dto.isMomentCommentStatus();
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.momentStatus = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeCommentStatus(boolean enabled) {
        this.momentCommentStatus = enabled;
        this.updatedAt = LocalDateTime.now();
    }
}
