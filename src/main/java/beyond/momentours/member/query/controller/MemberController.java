package beyond.momentours.member.query.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.member.query.service.MemberQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("queryMemberController")
@RequestMapping("api/member")
public class MemberController {

    private final MemberQueryService memberQueryService;

    @Autowired
    public MemberController(MemberQueryService memberQueryService) {
        this.memberQueryService = memberQueryService;
    }

    // 확인용으로 만들었습니다. 나중에 삭제할게요!
    @GetMapping("")
    public ResponseDTO<?> findByMemberId(@AuthenticationPrincipal CustomUserDetails user) {

        if (user == null) {
            // user가 null일 경우 처리를 추가할 수 있습니다.
            return ResponseDTO.ok("인증 정보가 없습니다.");
        }

        log.debug("회원 이메일: " + user.getUsername());  // email을 사용

        String memberEmail = user.getUsername();
        Long memId = memberQueryService.findByMemberId(memberEmail);

        return ResponseDTO.ok(memId);
    }
}
