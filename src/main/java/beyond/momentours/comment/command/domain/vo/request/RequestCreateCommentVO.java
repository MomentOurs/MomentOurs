package beyond.momentours.comment.command.domain.vo.request;

import beyond.momentours.comment.command.domain.aggregate.CommentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestCreateCommentVO {
    @JsonProperty("comment_content")
    private String commentContent;

    @JsonProperty("comment_type")
    private CommentType commentType;

    @JsonProperty("moment_id")
    private Long momentId;

    @JsonProperty("couple_log_id")
    private Long coupleLogId;
}