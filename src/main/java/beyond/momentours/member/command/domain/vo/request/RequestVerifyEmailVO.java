package beyond.momentours.member.command.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestVerifyEmailVO {
    @JsonProperty("member_email")
    private String memberEmail;

    @JsonProperty("code")
    private String code;
}
