package beyond.momentours.member.query.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.member.query.dto.MemberDTO;
import beyond.momentours.member.query.mapper.MemberQueryConverter;
import beyond.momentours.member.query.service.MemberQueryService;
import beyond.momentours.member.query.vo.request.RequestIdVO;
import beyond.momentours.member.query.vo.response.ResponseMypageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("queryMemberController")
@RequestMapping("api/member")
public class MemberController {

    private final MemberQueryService memberQueryService;
    private final MemberQueryConverter memberQueryConverter;

    @Autowired
    public MemberController(MemberQueryService memberQueryService, MemberQueryConverter memberQueryConverter) {
        this.memberQueryService = memberQueryService;
        this.memberQueryConverter = memberQueryConverter;
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

    /* 회원정보 조회 */
    @GetMapping("/mypage")
    public ResponseDTO<?> getMypage(@AuthenticationPrincipal CustomUserDetails user) {
        String email = user.getUsername();
        MemberDTO memberDTO = memberQueryService.findMemberEmailByMypage(email);
        ResponseMypageVO response = memberQueryConverter.fromDtoToMypageVO(memberDTO);
        return ResponseDTO.ok(response);
    }
}
