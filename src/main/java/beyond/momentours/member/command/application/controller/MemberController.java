package beyond.momentours.member.command.application.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.member.command.application.dto.LoginDTO;
import beyond.momentours.member.command.application.dto.MemberDTO;
import beyond.momentours.member.command.application.mapper.MemberConverter;
import beyond.momentours.member.command.application.service.MemberService;
import beyond.momentours.member.domain.vo.reponse.ResponseLoginMemberVO;
import beyond.momentours.member.domain.vo.reponse.ResponseSignupMemberVO;
import beyond.momentours.member.domain.vo.request.RequestLoginMemberVO;
import beyond.momentours.member.domain.vo.request.RequestSignupMemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("signup")
    public ResponseDTO<?> signup(@RequestBody RequestSignupMemberVO requestSignupMemberVO) {

        MemberDTO requestMemberDTO = memberConverter.fromSignupVOToDTO(requestSignupMemberVO);
        MemberDTO responseMemberDTO = memberService.signup(requestMemberDTO);
        ResponseSignupMemberVO response = memberConverter.fromDTOToSignupVO(responseMemberDTO);

        return ResponseDTO.ok(response);
    }

    @PostMapping("/login")
    public ResponseDTO<?> login(@RequestBody RequestLoginMemberVO requestLoginMemberVO) {

        log.info("이이이ㅣ잉: " + requestLoginMemberVO);

        MemberDTO requestMemberDTO = memberConverter.fromLoginVOToDTO(requestLoginMemberVO);
        LoginDTO responseMemberDTO = memberService.login(requestMemberDTO);
        ResponseLoginMemberVO responseLoginMemberVO = memberConverter.fromDTOToLoginVO(responseMemberDTO);

        return ResponseDTO.ok(responseLoginMemberVO);
    }



}
