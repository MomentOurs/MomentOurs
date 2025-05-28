package beyond.momentours.member.query.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestIdVO {
    @JsonProperty("member_email")
    private String memberEmail;

    @JsonProperty("member_name")
    private String memberName;

    @JsonProperty("member_birth")
    private LocalDate memberBirth;
}
