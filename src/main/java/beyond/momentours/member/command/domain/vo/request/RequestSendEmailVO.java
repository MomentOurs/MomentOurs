package beyond.momentours.member.command.domain.vo.request;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestSendEmailVO {
    private String memberEmail;
}
