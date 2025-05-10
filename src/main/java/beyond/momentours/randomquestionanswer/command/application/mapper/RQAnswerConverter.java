package beyond.momentours.randomquestionanswer.command.application.mapper;

import beyond.momentours.randomquestionanswer.command.application.dto.RQAnswerDTO;
import beyond.momentours.randomquestionanswer.command.domain.aggregate.entity.RQAnswer;
import beyond.momentours.randomquestionanswer.command.domain.vo.request.RequestRQAnswerUpdatedVO;
import org.springframework.stereotype.Component;

@Component
public class RQAnswerConverter {

    public RQAnswer dtoToEntity(RQAnswerDTO rqAnswerDTO, Long memberId){

        return RQAnswer.builder()
                .quesAnsContent(rqAnswerDTO.getQuesAnsContent())
                .userQuesId(rqAnswerDTO.getUserQuesId())
                .memberId(memberId)
                .build();
    }

    public RQAnswerDTO updateVoToDTO(RequestRQAnswerUpdatedVO requestRQAnswerUpdatedVO, Long quesAnswerId) {
        return RQAnswerDTO.builder()
                .quesAnswerId(quesAnswerId)
                .quesAnsContent(requestRQAnswerUpdatedVO.getQuesAnsContent())
                .build();
    }

}
