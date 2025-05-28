package beyond.momentours.member.query.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseMypageVO {
    @JsonProperty("member_email")
    private String memberEmail;

    @JsonProperty("member_name")
    private String memberName;

    @JsonProperty("member_nickname")
    private String memberNickname;

    @JsonProperty("member_birth")
    private LocalDate memberBirth;

    @JsonProperty("member_gender")
    private String memberGender;

    @JsonProperty("member_mbti")
    private String memberMbti;
}
