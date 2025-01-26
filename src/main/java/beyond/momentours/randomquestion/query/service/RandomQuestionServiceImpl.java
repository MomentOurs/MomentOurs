package beyond.momentours.randomquestion.query.service;

import beyond.momentours.randomquestion.query.dto.RandomQuestionDTO;
import beyond.momentours.randomquestion.query.repository.RandomQuestionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("RandomQuestionQueryService")
@RequiredArgsConstructor
public class RandomQuestionServiceImpl implements RandomQuestionService{

    private final RandomQuestionMapper randomQuestionMapper;

    @Override
    public PageInfo<RandomQuestionDTO> getRandomQuestion(Long memberId, int page, int size){
        PageHelper.startPage(page, size);
        List<RandomQuestionDTO> randomQuestions = randomQuestionMapper.getRandomQuestionByMemberId(memberId);
        return new PageInfo<>(randomQuestions);
    }
}
