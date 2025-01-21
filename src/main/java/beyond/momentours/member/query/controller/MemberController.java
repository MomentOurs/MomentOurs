package beyond.momentours.member.query.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.member.command.application.dto.MemberDTO;
import beyond.momentours.member.query.service.MemberQueryService;
import beyond.momentours.member.query.vo.request.RequestIdVO;
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

    /* 아이디 찾기 */
    @GetMapping("id")
    public ResponseDTO<?> getMemberEmail(@RequestBody RequestIdVO requestIdVO) {
        MemberDTO memberDTO = MemberDTO.builder()
                .memberEmail(requestIdVO.getMemberEmail())
               .build();
        String email = memberQueryService.findByMemberEmail(memberDTO);
        return ResponseDTO.ok(email);
    }
}
