package beyond.momentours.comment.command.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUpdateCommentVO {
    @JsonProperty("comment_id")
    private Long commentId;

    @JsonProperty("comment_content")
    private String commentContent;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
