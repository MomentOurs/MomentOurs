package beyond.momentours.comment.command.domain.aggregate.entity;

import beyond.momentours.comment.command.domain.aggregate.CommentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_comment")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "comment_content", nullable = false)
    private String commentContent;

    @Enumerated(EnumType.STRING)
    @Column(name = "comment_type", nullable = false)
    private CommentType commentType;

    @Column(name = "comment_status", nullable = false)
    private Boolean commentStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "moment_id")
    private Long momentId;

    @Column(name = "couple_log_id")
    private Long coupleLogId;

    @PrePersist
    public void prePersist() {
        if (commentStatus == null) {
            commentStatus = true;
        }
    }

    public void create(Comment comment) {
        comment.createdAt = LocalDateTime.now();
        comment.updatedAt = LocalDateTime.now();
    }
}
