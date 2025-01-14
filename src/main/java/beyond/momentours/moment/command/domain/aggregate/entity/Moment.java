package beyond.momentours.moment.command.domain.aggregate.entity;

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

    @Column(name = "moment_title")
    private String momentTitle;

    @Column(name = "moment_category")
    private String momentCategory;

    @Column(name = "moment_content")
    private String momentContent;

    @Column(name = "moment_disclosure")
    private boolean momentDisclosure;

    @Column(name = "moment_comment_status")
    private boolean momentCommentStatus;

    @Column(name = "moment_like")
    private int momentLike;

    @Column(name = "moment_view")
    private int momentView;

    @Column(name = "moment_status")
    private boolean momentStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "member_id")
    private Long memberId;

    // 상태 변경 method

    /* 추억 생성 시 초기 정보 설정 */
    public void createMoment(Long locationId, Long memberId) {
        this.locationId = locationId;
        this.memberId = memberId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.momentStatus = true;
        this.momentLike = 0;
        this.momentView = 0;
    }

}
