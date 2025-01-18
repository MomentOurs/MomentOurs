package beyond.momentours.comment.command.application.dto;

import beyond.momentours.comment.command.domain.aggregate.CommentType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CommentDTO {
    private Long commentId;
    private String commentContent;
    private CommentType commentType;
    private Boolean commentStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long memberId;
    private Long momentId;
    private Long coupleLogId;
}
