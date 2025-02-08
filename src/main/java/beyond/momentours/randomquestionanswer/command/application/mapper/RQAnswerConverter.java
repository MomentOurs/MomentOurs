package beyond.momentours.randomquestionanswer.command.application.mapper;

import beyond.momentours.randomquestionanswer.command.application.dto.RQAnswerDTO;
import beyond.momentours.randomquestionanswer.command.domain.aggregate.entity.RQAnswer;
import org.springframework.stereotype.Component;

@Component
public class RQAnswerConverter {

    public RQAnswerDTO entityToDTO(RQAnswer rqAnswer){

        RQAnswerDTO rqAnswerDTO = RQAnswerDTO.builder()
                .;

        rqAnswerDTO.setQuesAnswerId(rqAnswer.getQuesAnswerId());
        rqAnswerDTO.setQuesAnsContent(rqAnswer.getQuesAnsContent());
        rqAnswerDTO.setCreatedAt(rqAnswer.getCreatedAt());
        rqAnswerDTO.setUpdatedAt(rqAnswer.getUpdatedAt());
        rqAnswerDTO.setQuesId(rqAnswer.getQuesId());
        rqAnswerDTO.setMemberId(rqAnswer.getMemberId());

        return rqAnswerDTO;
    }

    public RQAnswer dtoToEntity(RQAnswerDTO rqAnswerDTO, Long memberId){

        return RQAnswer.builder()
                .quesAnsContent(rqAnswerDTO.getQuesAnsContent())
                .userQuesId(rqAnswerDTO.getUserQuesId())
                .memberId(memberId)
                .build();
    }
}
