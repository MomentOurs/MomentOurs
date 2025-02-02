package beyond.momentours.randomquestion.query.service;

import beyond.momentours.member.command.application.dto.CustomUserDetails;
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
    public PageInfo<RandomQuestionDTO> getRandomQuestion(int page, int size, CustomUserDetails user){
        Long memberId = user.getMemberId();
        PageHelper.startPage(page, size);
        List<RandomQuestionDTO> randomQuestions = randomQuestionMapper.getRandomQuestionByMemberId(memberId);
        return new PageInfo<>(randomQuestions);
    }

    @Override
    public RandomQuestionDTO getRandomQuestionDetail(Long quesId, CustomUserDetails user) {
        Long memberId = user.getMemberId();
        return randomQuestionMapper.getRandomQuestionByQuesId(memberId, quesId);
    }
}
