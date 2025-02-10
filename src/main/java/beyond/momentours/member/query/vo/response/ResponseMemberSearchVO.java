package beyond.momentours.member.query.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseMemberSearchVO {
    @JsonProperty("member_email")
    private String memberEmail;

    @JsonProperty("member_nickname")
    private String memberNickname;
}
