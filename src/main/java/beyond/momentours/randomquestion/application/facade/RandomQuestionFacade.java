package beyond.momentours.randomquestion.application.facade;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.couple.query.service.QueryCoupleService;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.randomquestion.command.application.service.QuestionService;
import beyond.momentours.randomquestion.query.dto.UserRandomQuestionDTO;
import beyond.momentours.randomquestion.query.repository.RandomQuestionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class RandomQuestionFacade {

    private final QueryCoupleService queryCoupleService;
    private final QuestionService questionService;
    private final RandomQuestionMapper randomQuestionMapper;

    public RandomQuestionFacade(QueryCoupleService queryCoupleService, QuestionService questionService, RandomQuestionMapper randomQuestionMapper) {
        this.queryCoupleService = queryCoupleService;
        this.questionService = questionService;
        this.randomQuestionMapper = randomQuestionMapper;
    }


    public UserRandomQuestionDTO getOrAssignLatest(CustomUserDetails user) throws ExecutionException, InterruptedException, TimeoutException {
        Long memberId = user.getMemberId();
        Long coupleId = queryCoupleService.getCoupleIdByMemberId(memberId);

        // 최신 질문 조회
        UserRandomQuestionDTO result = randomQuestionMapper.findByAnsStatus(coupleId);

        // 질문이 없다면 배정 후 재조회
        if (result == null) {
            questionService.assignNewQuestionToCouple(coupleId);
            result = randomQuestionMapper.findByAnsStatus(coupleId);
            if (result == null) {
                throw new CommonException(ErrorCode.NOT_FOUND_COUPLE_QUESTION);
            }
        }
        return result;
    }
}
