package beyond.momentours.randomquestion.query.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.randomquestion.query.service.RandomQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/randomquestion")
@RequiredArgsConstructor
public class RandomQuestionController {

    private final RandomQuestionService randomQuestionService;
    // createPlan
    //updatePlan
    //deletePlan
    //getPlanBy~~

    // answer_status는 초기값이 0이고, 둘다 답변하면 1이고, 한명만 답변했다면 그 사람의 회원번호
    @GetMapping("/")
    public ResponseDTO<?> getRandomQuestion(){
        return null;
    }
}
