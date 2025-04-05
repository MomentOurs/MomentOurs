package beyond.momentours.randomquestion.query.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.randomquestion.query.dto.RandomQuestionDTO;
import beyond.momentours.randomquestion.query.dto.UserRandomQuestionDTO;
import beyond.momentours.randomquestion.query.service.RandomQuestionService;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/random-question")
@RequiredArgsConstructor
public class RandomQuestionController {

    private final RandomQuestionService randomQuestionService;

    @GetMapping("")
    public ResponseDTO<?> getRandomQuestion(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseDTO.ok(randomQuestionService.getRandomQuestion(user));
    }

    // answer_status는 초기값이 0이고, 둘다 답변하면 1이고, 한명만 답변했다면 그 사람의 회원번호
    @GetMapping("/list")
    public ResponseDTO<?> getRandomQuestionList(
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @AuthenticationPrincipal CustomUserDetails user) {

        List<UserRandomQuestionDTO> list = randomQuestionService
                .getRandomQuestionListByCursor(lastId, size, keyword, user);

        Long nextCursorId = list.isEmpty() ? null : list.get(list.size() - 1).getUserQuesId();
        boolean hasNext = list.size() == size;

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("nextCursorId", nextCursorId);
        result.put("hasNext", hasNext);

        return ResponseDTO.ok(result);
    }


    @GetMapping("/{quesId}")
    public ResponseDTO<?> getRandomQuestionDetail(@PathVariable Long quesId,
                                                  @AuthenticationPrincipal CustomUserDetails user){
        return ResponseDTO.ok(randomQuestionService.getRandomQuestionDetail(quesId,user));
    }
}
