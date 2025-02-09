package beyond.momentours.randomquestionanswer.query.service;

import beyond.momentours.randomquestionanswer.query.dto.RQAnswerDTO;

public interface RQAnswerQueryService {
    RQAnswerDTO findByQuesAnswerIdAndMemberId(Long quesAnswerId, Long memberId);
}
