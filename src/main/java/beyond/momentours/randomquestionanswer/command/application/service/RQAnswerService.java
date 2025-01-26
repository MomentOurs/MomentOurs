package beyond.momentours.randomquestionanswer.command.application.service;

import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.randomquestionanswer.command.application.dto.RQAnswerDTO;

public interface RQAnswerService {
    RQAnswerDTO createRQAnswer(RQAnswerDTO rqAnswerDTO, CustomUserDetails user);
}
