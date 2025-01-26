package beyond.momentours.randomquestion.query.repository;

import beyond.momentours.randomquestion.query.dto.RandomQuestionDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RandomQuestionMapper {
    List<RandomQuestionDTO> getRandomQuestionByMemberId(Long memberId);
}
