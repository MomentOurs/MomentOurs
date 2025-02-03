package beyond.momentours.pre_blacklist.query.dto;

import beyond.momentours.member.query.dto.MemberDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PreBlackListDTO {

    private Long preBlackId;
    private int blackListCount;
    private String status;
    private LocalDateTime createdAt;
    private Long memberId;
    private Long reportId;

    private MemberDTO member;
}
