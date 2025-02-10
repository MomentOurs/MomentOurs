package beyond.momentours.randomquestionanswer.query.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.randomquestionanswer.query.service.RQAnswerQueryService;
import beyond.momentours.randomquestionanswer.query.vo.response.ResponseRQAnswerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("queryRQAnswerController")
@RequestMapping("/api/randomquestion-answer")
public class RQAnswerQueryController {
    private final RQAnswerQueryService rqAnswerQueryService;

    @Autowired
    public RQAnswerQueryController(RQAnswerQueryService rqAnswerQueryService) {
        this.rqAnswerQueryService = rqAnswerQueryService;
    }

    @GetMapping("{userQuesId}")
    public ResponseDTO<?> getRQAnswer(@PathVariable Long userQuesId,
                                      @AuthenticationPrincipal CustomUserDetails user){
        ResponseRQAnswerVO response = rqAnswerQueryService.getRQAnswer(userQuesId, user);
        return ResponseDTO.ok(response);
    }

}
