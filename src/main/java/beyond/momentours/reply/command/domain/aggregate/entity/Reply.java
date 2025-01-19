package beyond.momentours.reply.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_reply")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId;

    @Column(name = "reply_content", nullable = false)
    private String replyContent;

    @Column(name = "reply_status", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean replyStatus = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    public void create(Reply reply) {
        reply.createdAt = LocalDateTime.now();
        reply.updatedAt = LocalDateTime.now();
    }

    public void updateContent(String commentContent) {
        this.replyContent = commentContent;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateStatus(boolean status) {
        this.replyStatus = status;
        this.updatedAt = LocalDateTime.now();
    }
}