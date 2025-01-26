package beyond.momentours.randomquestion.query.repository;

import beyond.momentours.randomquestion.query.dto.RandomQuestionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RandomQuestionMapper {
    List<RandomQuestionDTO> getRandomQuestionByMemberId(Long memberId);

    RandomQuestionDTO getRandomQuestionByQuesId(@Param("memberId") Long memberId, @Param("quesId") Long quesId);
}
