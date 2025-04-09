package beyond.momentours.blacklist.query.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseBlacklistAll {

    @JsonProperty("member_id")
    private Long memberId;

    @JsonProperty("black_status")
    private Boolean blackStatus;

    @JsonProperty("black_date")
    private LocalDateTime blackDate;

    @JsonProperty("accessible_date")
    private LocalDateTime accessibleDate;

    @JsonProperty("post_id")
    private Long postId;

    @JsonProperty("post_category")
    private String postCategory;
}
