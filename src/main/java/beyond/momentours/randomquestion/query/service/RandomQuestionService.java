package beyond.momentours.randomquestion.query.service;

import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.randomquestion.query.dto.RandomQuestionDTO;
import com.github.pagehelper.PageInfo;

public interface RandomQuestionService {
    PageInfo<RandomQuestionDTO> getRandomQuestion(int page, int size, CustomUserDetails user);

    RandomQuestionDTO getRandomQuestionDetail(Long quesId, CustomUserDetails user);
}
