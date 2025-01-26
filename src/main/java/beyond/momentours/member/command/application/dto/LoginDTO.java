package beyond.momentours.member.command.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {
    private String memberEmail;
    private String memberPassword;
//    private String accessToken;
//    private String refreshToken;
}
