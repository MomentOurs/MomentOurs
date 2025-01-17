package beyond.momentours.comment.command.domain.vo.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUpdateCommentVO {
    private Long commentId;         // 댓글 ID
    private String commentContent; // 수정된 댓글 내용
    private LocalDateTime updatedAt; // 수정 시간
}
