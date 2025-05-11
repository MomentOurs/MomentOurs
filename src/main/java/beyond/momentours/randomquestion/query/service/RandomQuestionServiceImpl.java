package beyond.momentours.randomquestion.query.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.couple.query.service.QueryCoupleService;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.randomquestion.command.domain.aggregate.entity.RandomQuestion;
import beyond.momentours.randomquestion.query.dto.RandomQuestionDTO;
import beyond.momentours.randomquestion.query.dto.UserRandomQuestionDTO;
import beyond.momentours.randomquestion.query.repository.RandomQuestionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public List<UserRandomQuestionDTO> getRandomQuestionListByCursor(Long lastId, int size, String keyword, CustomUserDetails user) {
        Long memberId = user.getMemberId();
        Long coupleId = queryCoupleService.getCoupleIdByMemberId(memberId);

        return randomQuestionMapper.getRandomQuestionByCursor(coupleId, lastId, keyword, size);
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
    public UserRandomQuestionDTO getRandomQuestion(CustomUserDetails user) {
        Long memberId = user.getMemberId();
        Long coupleId = queryCoupleService.getCoupleIdByMemberId(memberId);
        UserRandomQuestionDTO result = randomQuestionMapper.findByAnsStatus(coupleId);
        if (result == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_COUPLE_QUESTION);
        }
        return result;
    }

    // 커플에 배정된 랜덤질문의 가장 높은 couple_ques_no 조회
    @Override
    public Long findQuestionsByMemberId(Long coupleId) {
        return randomQuestionMapper.findQuestionsByMemberId(coupleId);
    }

    // 특정 커플의 특정 질문 번호에 대해 상태가 'ALL'이 있는지 확인
    @Override
    public boolean existsByCoupleIdAndCoupleQuesNoAndAnsStatus(Long coupleId, Long coupleQuesNo, String ansStatus) {
        return randomQuestionMapper.existsByCoupleIdAndCoupleQuesNoAndAnsStatus(coupleId, coupleQuesNo, ansStatus);
    }

}
