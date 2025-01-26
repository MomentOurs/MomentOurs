package beyond.momentours.member.query.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestIdVO {
    @JsonProperty("member_email")
    private String memberEmail;
}
