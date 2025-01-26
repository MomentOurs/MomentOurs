package beyond.momentours.member.command.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestSendEmailVO {
    @JsonProperty("member_email")
    private String memberEmail;
}
