package beyond.momentours.member.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestSignupMemberVO {

    @JsonProperty("member_id")
    private Long memberId;

    @JsonProperty("member_email")
    private String memberEmail;

    @JsonProperty("member_password")
    private String memberPassword;

    @JsonProperty("member_name")
    private String memberName;

    @JsonProperty("member_nickname")
    private String memberNickname;

    @JsonProperty("member_phone")
    private String memberPhone;

    @JsonProperty("member_birth")
    private String memberBirth;

    @JsonProperty("member_gender")
    private String memberGender;

    @JsonProperty("member_mbti")
    private String memberMbti;
}
