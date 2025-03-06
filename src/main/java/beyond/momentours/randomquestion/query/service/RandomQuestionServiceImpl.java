package beyond.momentours.randomquestion.query.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.couple.query.service.QueryCoupleService;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.randomquestion.command.domain.aggregate.entity.RandomQuestion;
import beyond.momentours.randomquestion.query.dto.RandomQuestionDTO;
import beyond.momentours.randomquestion.query.repository.RandomQuestionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("RandomQuestionQueryService")
public class RandomQuestionServiceImpl implements RandomQuestionService{

    private final RandomQuestionMapper randomQuestionMapper;
    private final QueryCoupleService queryCoupleService;

    public RandomQuestionServiceImpl(RandomQuestionMapper randomQuestionMapper, QueryCoupleService queryCoupleService) {
        this.randomQuestionMapper = randomQuestionMapper;
        this.queryCoupleService = queryCoupleService;
    }

    @Override
    public PageInfo<RandomQuestionDTO> getRandomQuestionList(int page, int size, CustomUserDetails user){
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

    @Override
    public List<RandomQuestion> findAllQuestions() {
        return randomQuestionMapper.findAllQuestions();
    }

    @Override
    public List<Long> findUsedQuestionsByCoupleId(Long coupleId) {
        List<Long> usedQuestions = randomQuestionMapper.findUsedQuestionsByCoupleId(coupleId);
        if (usedQuestions.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_COUPLE_QUESTION);
        }
        return usedQuestions;
    }

    // 처음 랜덤질문 조회 화면
    @Override
    public RandomQuestionDTO getRandomQuestion(CustomUserDetails user) {
        Long memberId = user.getMemberId();
        Long coupleId = queryCoupleService.getCoupleIdByMemberId(memberId);
        RandomQuestionDTO result = randomQuestionMapper.findByAnsStatus(coupleId);
        if (result == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_COUPLE_QUESTION);
        }
        return result;
    }

}
