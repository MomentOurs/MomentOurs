package beyond.momentours.randomquestion.query.repository;

import beyond.momentours.randomquestion.command.domain.aggregate.entity.RandomQuestion;
import beyond.momentours.randomquestion.command.domain.aggregate.entity.UserRandomQuestion;
import beyond.momentours.randomquestion.query.dto.RandomQuestionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RandomQuestionMapper {
    List<RandomQuestionDTO> getRandomQuestionByMemberId(Long memberId);

    RandomQuestionDTO getRandomQuestionByQuesId(@Param("memberId") Long memberId, @Param("quesId") Long quesId);

    List<RandomQuestion> findAllQuestions();

    List<Long> findUsedQuestionsByCoupleId(@Param("coupleId") Long coupleId);

    UserRandomQuestion findByQuestionId(@Param("quesId") Long quesId);
}
