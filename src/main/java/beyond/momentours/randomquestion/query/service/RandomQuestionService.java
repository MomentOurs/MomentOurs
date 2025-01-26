package beyond.momentours.randomquestion.query.service;

import beyond.momentours.randomquestion.query.dto.RandomQuestionDTO;
import com.github.pagehelper.PageInfo;

public interface RandomQuestionService {
    PageInfo<RandomQuestionDTO> getRandomQuestion(Long memberId, int page, int size);
}
