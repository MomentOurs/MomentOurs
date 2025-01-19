package beyond.momentours.reply.command.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseCreateReplyVO {

    @JsonProperty("reply_content")
    private String replyContent;

    @JsonProperty("comment_id")
    private Long commentId;
}
