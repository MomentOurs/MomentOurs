package beyond.momentours.randomquestion.query.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.randomquestion.query.dto.RandomQuestionDTO;
import beyond.momentours.randomquestion.query.service.RandomQuestionService;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/randomquestion")
@RequiredArgsConstructor
public class RandomQuestionController {

    private final RandomQuestionService randomQuestionService;
    // createPlan
    //updatePlan
    //deletePlan
    //getPlanBy~~

    // answer_status는 초기값이 0이고, 둘다 답변하면 1이고, 한명만 답변했다면 그 사람의 회원번호
    @GetMapping("/")
    public ResponseDTO<?> getRandomQuestion(@RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @AuthenticationPrincipal CustomUserDetails user){
        PageInfo<RandomQuestionDTO> randomQuestions = randomQuestionService.getRandomQuestion(page, size, user);

        return ResponseDTO.ok(randomQuestions);
    }

    @GetMapping("/{quesId}")
    public ResponseDTO<?> getRandomQuestionDetail(@PathVariable Long quesId,
                                                  @AuthenticationPrincipal CustomUserDetails user){
        return ResponseDTO.ok(randomQuestionService.getRandomQuestionDetail(quesId,user));
    }
}
