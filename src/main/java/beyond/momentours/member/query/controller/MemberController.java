package beyond.momentours.member.query.controller;

import beyond.momentours.member.query.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Long findByMemberId(@RequestParam Long memberId) {
        Long memId = memberService.findByMemberId(memberId);

        return memId;
    }
}
