package beyond.momentours.randomquestion.command.application.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.randomquestion.command.application.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController("commandRandomQuestionController")
@RequestMapping("api/random-question")
public class RandomQuestionController {
    private final QuestionService questionService;

    @Autowired
    public RandomQuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("assign/{coupleId}")
    public ResponseDTO<?> createRandomQuestionByCoupleId(@PathVariable Long coupleId) throws InterruptedException, ExecutionException, TimeoutException {
        questionService.createNewQuestion(coupleId);
        return ResponseDTO.ok("질문이 배정되었습니다.");
    }
}
