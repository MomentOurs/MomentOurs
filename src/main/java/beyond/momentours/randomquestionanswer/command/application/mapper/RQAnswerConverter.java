package beyond.momentours.randomquestionanswer.command.application.mapper;

import beyond.momentours.randomquestionanswer.command.application.dto.RQAnswerDTO;
import beyond.momentours.randomquestionanswer.command.domain.aggregate.entity.RQAnswer;
import org.springframework.stereotype.Component;

@Component
public class RQAnswerConverter {

    public RQAnswerDTO entityToDTO(RQAnswer rqAnswer){

        RQAnswerDTO rqAnswerDTO = new RQAnswerDTO();

        rqAnswerDTO.setQuesAnswerId(rqAnswer.getQuesAnswerId());
        rqAnswerDTO.setQuesAnsContent(rqAnswer.getQuesAnsContent());
        rqAnswerDTO.setCreatedAt(rqAnswer.getCreatedAt());
        rqAnswerDTO.setUpdatedAt(rqAnswer.getUpdatedAt());
        rqAnswerDTO.setQuesId(rqAnswer.getQuesId());
        rqAnswerDTO.setMemberId(rqAnswer.getMemberId());

        return rqAnswerDTO;
    }

    public RQAnswer dtoToEntity(RQAnswerDTO rqAnswerDTO){

        RQAnswer rqAnswer = RQAnswer.builder()
                .quesAnswerId(rqAnswerDTO.getQuesAnswerId())
                .quesAnsContent(rqAnswerDTO.getQuesAnsContent())
                .createdAt(rqAnswerDTO.getCreatedAt())
                .updatedAt(rqAnswerDTO.getUpdatedAt())
                .quesId(rqAnswerDTO.getQuesId())
                .memberId(rqAnswerDTO.getMemberId())
                .build();

        return rqAnswer;
    }
}
