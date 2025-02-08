package beyond.momentours.randomquestionanswer.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.randomquestionanswer.command.application.dto.RQAnswerDTO;
import beyond.momentours.randomquestionanswer.command.application.mapper.RQAnswerConverter;
import beyond.momentours.randomquestionanswer.command.domain.aggregate.entity.RQAnswer;
import beyond.momentours.randomquestionanswer.command.domain.repository.RQAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RQAnswerServiceImpl implements RQAnswerService {

    private final RQAnswerConverter rqAnswerConverter;
    private final RQAnswerRepository rqAnswerRepository;

    @Override
    @Transactional
    public void createRQAnswer(RQAnswerDTO rqAnswerDTO, CustomUserDetails user) {
        try {
            Long memberId = user.getMemberId();
            RQAnswer rqAnswer = rqAnswerConverter.dtoToEntity(rqAnswerDTO, memberId);

            rqAnswerRepository.save(rqAnswer);
        } catch (Exception e) {
            throw new CommonException(ErrorCode.RANDOMQUES_ANSWER_FAILURE);
        }
    }
}
