package beyond.momentours.randomquestionanswer.query.repository;

import beyond.momentours.randomquestionanswer.query.dto.RQAnswerDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RQAnswerMapper {
    List<RQAnswerDTO> findByQuesAnswerId(Long userQuesId);
}
