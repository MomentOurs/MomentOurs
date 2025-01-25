package beyond.momentours.blacklist.command.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestBlackVO {

    @JsonProperty("member_id")
    private Long memberId;

    @JsonProperty("black_list_Days")
    private Integer blacklistDays;
}
