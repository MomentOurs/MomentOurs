package beyond.momentours.member.command.domain.vo.request;

import beyond.momentours.member.command.domain.aggregate.entity.MemberRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestUpdateProfileMemberVO {

    @JsonProperty("member_password")
    private String memberPassword;

    @JsonProperty("member_nickname")
    private String memberNickname;

    @JsonProperty("member_birth")
    private LocalDate memberBirth;

    @JsonProperty("member_gender")
    private String memberGender;

    @JsonProperty("member_mbti")
    private String memberMbti;

}
