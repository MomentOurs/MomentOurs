package beyond.momentours.randomquestionanswer.command.application.service;

import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.randomquestionanswer.command.application.dto.RQAnswerDTO;

public interface RQAnswerService {
    void createRQAnswer(RQAnswerDTO rqAnswerDTO, CustomUserDetails user);

    void updateRQAnswer(RQAnswerDTO rqAnswerDTO, CustomUserDetails user);
}
