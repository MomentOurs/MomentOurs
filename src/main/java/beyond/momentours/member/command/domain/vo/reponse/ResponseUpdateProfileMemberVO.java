package beyond.momentours.member.command.domain.vo.reponse;

import beyond.momentours.member.command.domain.aggregate.entity.MemberRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUpdateProfileMemberVO {

    @JsonProperty("member_email")
    private String memberEmail;

    @JsonProperty("member_password")
    private String memberPassword;

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

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("member_role")
    private MemberRole memberRole;
}
