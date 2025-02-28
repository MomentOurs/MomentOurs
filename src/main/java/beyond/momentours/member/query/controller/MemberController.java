package beyond.momentours.member.query.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.member.query.dto.MemberDTO;
import beyond.momentours.member.query.mapper.MemberQueryConverter;
import beyond.momentours.member.query.service.MemberQueryService;
import beyond.momentours.member.query.vo.request.RequestIdVO;
import beyond.momentours.member.query.vo.response.ResponseMemberSearchVO;
import beyond.momentours.member.query.vo.response.ResponseMypageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("mypage")
    public ResponseDTO<?> getMypage(@AuthenticationPrincipal CustomUserDetails user) {
        MemberDTO memberDTO = memberQueryService.findMemberEmailByMypage(user);
        ResponseMypageVO response = memberQueryConverter.fromDtoToMypageVO(memberDTO);
        return ResponseDTO.ok(response);
    }

    /* 회원 아이디 & 닉네임 검색 */
    @GetMapping("search")
    public ResponseDTO<List<ResponseMemberSearchVO>> getSearch(@RequestParam(required = false) String memberNickname,
                                                               @RequestParam(required = false) String memberEmail) {
        List<ResponseMemberSearchVO> response = memberQueryService.getMemberSearch(memberNickname, memberEmail);
        return ResponseDTO.ok(response);
    }

    /* 이메일 중복확인 */
    @GetMapping("check-email")
    public ResponseDTO<?> emailCheck(@RequestParam String memberEmail) {
        boolean checkEmail = memberQueryService.emailCheck(memberEmail);

        if (checkEmail) {
            return ResponseDTO.ok("이미 존재하는 이메일입니다.");
        } else {
            return ResponseDTO.ok("사용할 수 있는 이메일입니다.");
        }
    }

    /* 닉네임 중복확인 */
    @GetMapping("check-nickname")
    public ResponseDTO<?> nicknameCheck(@RequestParam String memberNickname) {
        boolean checkNickname = memberQueryService.nicknameCheck(memberNickname);

        if (checkNickname) {
            return ResponseDTO.ok("이미 존재하는 닉네임입니다.");
        } else {
            return ResponseDTO.ok("사용할 수 있는 닉네임입니다.");
        }
    }

}
