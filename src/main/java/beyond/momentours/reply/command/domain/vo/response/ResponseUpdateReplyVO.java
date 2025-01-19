package beyond.momentours.reply.command.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUpdateReplyVO {

    @JsonProperty("reply_id")
    private Long replyId;

    @JsonProperty("reply_content")
    private String replyContent;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
