package beyond.momentours.moment.command.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestMomentDTO {

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

    @JsonProperty("member_id")
    private Long memberId;

    @JsonProperty("latitude")
    private BigDecimal latitude;

    @JsonProperty("longitude")
    private BigDecimal longitude;

    @JsonProperty("location_name")
    private String locationName;

}
