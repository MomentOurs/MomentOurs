package beyond.momentours.randomquestionanswer.query.service;

import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.randomquestionanswer.query.dto.RQAnswerDTO;
import beyond.momentours.randomquestionanswer.query.vo.response.ResponseRQAnswerVO;

public interface RQAnswerQueryService {
    ResponseRQAnswerVO getRQAnswer(Long userQuesId, CustomUserDetails user);
}
