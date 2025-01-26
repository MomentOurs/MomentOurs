package beyond.momentours.member.command.application.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EmailDTO {
    private String memberEmail;
    private String code;
}
