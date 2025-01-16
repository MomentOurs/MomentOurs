package beyond.momentours.member.query.controller;

import beyond.momentours.member.query.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("queryMemberController")
@RequestMapping("api/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 확인용으로 만들었습니다. 나중에 삭제할게요!
    @GetMapping("")
    public Long findByMemberId(@RequestAttribute("memberEmail") String memberEmail) {
        Long memId = memberService.findByMemberId(memberEmail);

        return memId;
    }
}
