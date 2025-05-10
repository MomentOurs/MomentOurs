package beyond.momentours.randomquestionanswer.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.randomquestionanswer.command.application.dto.RQAnswerDTO;
import beyond.momentours.randomquestionanswer.command.application.mapper.RQAnswerConverter;
import beyond.momentours.randomquestionanswer.command.domain.aggregate.entity.RQAnswer;
import beyond.momentours.randomquestionanswer.command.domain.repository.RQAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("commandRQAnswerServiceImpl")
public class RQAnswerServiceImpl implements RQAnswerService {

    private final RQAnswerConverter rqAnswerConverter;
    private final RQAnswerRepository rqAnswerRepository;

    @Autowired
    public RQAnswerServiceImpl(RQAnswerConverter rqAnswerConverter, RQAnswerRepository rqAnswerRepository) {
        this.rqAnswerConverter = rqAnswerConverter;
        this.rqAnswerRepository = rqAnswerRepository;
    }


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

    @Override
    @Transactional
    public void updateRQAnswer(RQAnswerDTO rqAnswerDTO, CustomUserDetails user) {
        try {
            Long memberId = user.getMemberId();
            RQAnswer rqAnswer = rqAnswerRepository.findByQuesAnswerIdAndMemberId(rqAnswerDTO.getQuesAnswerId(), memberId);
            if (rqAnswer == null) {
                throw new CommonException(ErrorCode.NOT_FOUND_QUES_ANSWER);
            }

            if (rqAnswerDTO.getQuesAnsContent() != null) {
                rqAnswerRepository.updateContent(rqAnswerDTO.getQuesAnswerId(), rqAnswerDTO.getQuesAnsContent());
            }
        } catch (CommonException e) {
            throw new CommonException(ErrorCode.QUES_ANSWER_UPDATE_FAILURE);
        }
    }

    @Override
    @Transactional
    public void deleteAnswer(Long userQuesId, CustomUserDetails user) {
        try {
            Long memberId = user.getMemberId();
            RQAnswer rqAnswer = rqAnswerRepository.findByUserQuesIdAndMemberId(userQuesId, memberId);
            if (rqAnswer == null) {
                throw new CommonException(ErrorCode.NOT_FOUND_QUES_ANSWER);
            }
            rqAnswerRepository.deleteById(rqAnswer.getQuesAnswerId());
        } catch (CommonException e) {
            throw new CommonException(ErrorCode.QUES_ANSWER_DELETE_FAILURE);
        }
    }
}
