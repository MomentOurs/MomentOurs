package beyond.momentours.moment.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "tb_moment")
@Entity
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
}
