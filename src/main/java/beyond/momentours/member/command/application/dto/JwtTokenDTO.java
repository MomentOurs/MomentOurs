package beyond.momentours.member.command.application.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenDTO {
    private String memberEmail;
}
