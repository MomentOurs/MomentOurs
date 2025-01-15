package beyond.momentours.member.command.domain.vo.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseLoginMemberVO {

    @JsonProperty("member_email")
    private String memberEmail;

    @JsonProperty("member_password")
    private String memberPassword;

//    @JsonProperty("access_token")
//    private String accessToken;
//
//    @JsonProperty("refresh_token")
//    private String refreshToken;

//    @JsonProperty("member_role")
//    private String memberRole;

}
