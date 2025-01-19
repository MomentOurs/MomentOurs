package beyond.momentours.reply.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReplyDTO {
    private Long replyId;
    private String replyContent;
    private Boolean replyStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long commentId;
    private Long memberId;
}
