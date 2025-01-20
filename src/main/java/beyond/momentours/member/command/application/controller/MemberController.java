package beyond.momentours.member.command.application.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.member.command.application.dto.MemberDTO;
import beyond.momentours.member.command.application.mapper.MemberConverter;
import beyond.momentours.member.command.application.service.MemberService;
import beyond.momentours.member.command.domain.vo.reponse.ResponseSignupMemberVO;
import beyond.momentours.member.command.domain.vo.reponse.ResponseUpdateProfileMemberVO;
import beyond.momentours.member.command.domain.vo.request.RequestSendEmailVO;
import beyond.momentours.member.command.domain.vo.request.RequestSignupMemberVO;
import beyond.momentours.member.command.domain.vo.request.RequestUpdateProfileMemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("commandMemberController")
@RequestMapping("api/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberConverter memberConverter;

    @Autowired
    public MemberController(MemberService memberService, MemberConverter memberConverter) {
        this.memberService = memberService;
        this.memberConverter = memberConverter;
    }

    /* 회원가입 */
    @PostMapping("signup")
    public ResponseDTO<?> createSignup(@RequestBody RequestSignupMemberVO requestSignupMemberVO) {

        MemberDTO requestMemberDTO = memberConverter.fromSignupVOToDTO(requestSignupMemberVO);
        MemberDTO responseMemberDTO = memberService.signup(requestMemberDTO);
        ResponseSignupMemberVO response = memberConverter.fromDTOToSignupVO(responseMemberDTO);

        return ResponseDTO.ok(response);
    }

    /* 회원 정보 수정 */
    @PatchMapping("mypage")
    public ResponseDTO<?> updateProfile(@RequestBody RequestUpdateProfileMemberVO requestUpdateProfileMemberVO,
                                        @AuthenticationPrincipal CustomUserDetails member) {
        String memberEmail = member.getUsername();
        MemberDTO requestMemberDTO = memberConverter.fromProfileVOToDTO(requestUpdateProfileMemberVO, memberEmail);
        MemberDTO responseMemberDTO = memberService.updateProfile(requestMemberDTO);
        ResponseUpdateProfileMemberVO response = memberConverter.fromDTOToUpdateProfile(responseMemberDTO);

        return ResponseDTO.ok(response);
    }

    /* 이메일 인증코드 발송 */
    @PostMapping("email/send")
    public ResponseDTO<?> sendEmail(@RequestBody RequestSendEmailVO requestSendEmailVO) {
        MemberDTO requestMemberDTO = MemberDTO.builder()
                .memberEmail(requestSendEmailVO.getMemberEmail())
                .build();
        String authCode = memberService.checkEmail(requestMemberDTO);
        return ResponseDTO.ok(authCode);
    }

}
