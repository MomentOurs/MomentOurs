package beyond.momentours.member.command.application.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.member.command.application.dto.MemberDTO;
import beyond.momentours.member.command.application.mapper.MemberConverter;
import beyond.momentours.member.command.application.service.MemberService;
import beyond.momentours.member.domain.vo.reponse.ResponseSignupMemberVO;
import beyond.momentours.member.domain.vo.request.RequestSignupMemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
