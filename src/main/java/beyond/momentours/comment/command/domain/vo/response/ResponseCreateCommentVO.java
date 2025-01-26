package beyond.momentours.comment.command.domain.vo.response;

import beyond.momentours.comment.command.domain.aggregate.CommentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseCreateCommentVO {
    @JsonProperty("comment_content")
    private String commentContent;

    @JsonProperty("comment_type")
    private CommentType commentType;

    @JsonProperty("ques_id")
    private Long quesId;

    @JsonProperty("couple_log_id")
    private Long coupleLogId;
}
