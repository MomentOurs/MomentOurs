package beyond.momentours.comment.command.domain.vo.request;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestUpdateCommentVO {
    private String commentContent;
}