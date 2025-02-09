package beyond.momentours.randomquestionanswer.query.repository;

import beyond.momentours.randomquestionanswer.query.dto.RQAnswerDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RQAnswerMapper {
    RQAnswerDTO findByQuesAnswerIdAndMemberId(@Param("quesAnswerId") Long quesAnswerId,
                                              @Param("memberId") Long memberId);
}
