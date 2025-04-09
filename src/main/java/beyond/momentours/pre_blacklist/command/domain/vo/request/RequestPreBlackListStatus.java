package beyond.momentours.pre_blacklist.command.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestPreBlackListStatus {

    @JsonProperty("pre_black_id")
    private Long preBlackId;

    @JsonProperty("status")
    private String status;
}
