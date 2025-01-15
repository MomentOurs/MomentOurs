package beyond.momentours.member.command.application.dto;

import beyond.momentours.member.domain.aggregate.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

    private Long memberId;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberNickname;
    private String memberPhone;
    private String memberBirth;
    private String memberGender;
    private String memberMbti;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean memberStatus;
    private MemberRole memberRole;

    public void encodedPwd(String encodedPwd) {
        this.memberPassword = encodedPwd;
    }

}
