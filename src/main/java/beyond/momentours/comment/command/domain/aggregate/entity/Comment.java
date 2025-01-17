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

    @Column(name = "comment_status", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean commentStatus = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "ques_ans_id")
    private Long questAnsId;

    @Column(name = "couple_log_id")
    private Long coupleLogId;

    public void create(Comment comment) {
        comment.createdAt = LocalDateTime.now();
        comment.updatedAt = LocalDateTime.now();
    }

    public void updateContent(String commentContent) {
        this.commentContent = commentContent;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateStatus(boolean status) {
        this.commentStatus = status;
        this.updatedAt = LocalDateTime.now();
    }
}
