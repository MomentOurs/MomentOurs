package beyond.momentours.moment.command.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MomentDTO {

    @JsonProperty("moment_id")
    private Long momentId;

    @JsonProperty("moment_title")
    private String momentTitle;

    @JsonProperty("moment_category")
    private String momentCategory;

    @JsonProperty("moment_content")
    private String momentContent;

    @JsonProperty("moment_certified")
    private boolean momentCertified;

    @JsonProperty("moment_comment_status")
    private boolean momentCommentStatus;

    @JsonProperty("moment_like")
    private Long momentLike;

    @JsonProperty("moment_view")
    private Long momentView;

    @JsonProperty("moment_status")
    private boolean momentStatus;

    @JsonProperty("moment_image_urls")
    private String momentImageUrls;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("location_id")
    private Long locationId;

    @JsonProperty("member_id")
    private Long memberId;

    @JsonProperty("couple_id")
    private Long coupleId;

    @JsonProperty("location_name")
    private String locationName;

    @JsonProperty("latitude")
    private BigDecimal latitude;

    @JsonProperty("longitude")
    private BigDecimal longitude;
}
