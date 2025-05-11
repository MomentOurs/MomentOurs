package beyond.momentours.randomquestion.query.service;

import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.randomquestion.command.domain.aggregate.entity.RandomQuestion;
import beyond.momentours.randomquestion.query.dto.UserRandomQuestionDTO;

import java.util.List;

public interface RandomQuestionService {
    List<UserRandomQuestionDTO> getRandomQuestionListByCursor(Long lastId, int size, String keyword, CustomUserDetails user);

    List<RandomQuestion> findAllQuestions();

    List<Long> findUsedQuestionsByCoupleId(Long coupleId);

    // 커플에 배정된 랜덤질문의 가장 높은 couple_ques_no 조회
    Long findQuestionsByMemberId(Long coupleId);

    boolean existsByCoupleIdAndCoupleQuesNoAndAnsStatus(Long coupleId, Long coupleQuesNo, String ansStatus);

    UserRandomQuestionDTO getRandomQuestionDetail(Long userQuesId, CustomUserDetails user);
}
