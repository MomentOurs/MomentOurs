package beyond.momentours.pre_blacklist.query.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponsePreBlacklistAll {
    @JsonProperty("blacklist_count")
    private Long blackListCount;

    @JsonProperty("status")
    private String status;

    @JsonProperty("pre_black_id")
    private String preBlackId;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("status")
    private String reportId;

    @JsonProperty("member_name")
    private String memberName;
}
