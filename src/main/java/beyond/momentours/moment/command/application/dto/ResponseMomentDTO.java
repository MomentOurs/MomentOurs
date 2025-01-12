package beyond.momentours.moment.command.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseMomentDTO {

    @JsonProperty("moment_id")
    private Long momentId;

    @JsonProperty("moment_title")
    private String momentTitle;

    @JsonProperty("moment_category")
    private String momentCategory;

    @JsonProperty("moment_content")
    private String momentContent;

    @JsonProperty("moment_disclosure")
    private boolean momentDisclosure;

    @JsonProperty("moment_comment_status")
    private boolean momentCommentStatus;

    @JsonProperty("moment_like")
    private int momentLike;

    @JsonProperty("moment_view")
    private int momentView;

    @JsonProperty("moment_status")
    private boolean momentStatus;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("location_id")
    private Long locationId;

    @JsonProperty("member_id")
    private Long memberId;

}
