package beyond.momentours.randomquestionanswer.command.application.service;

import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.randomquestionanswer.command.application.dto.RQAnswerDTO;
import beyond.momentours.randomquestionanswer.command.application.mapper.RQAnswerConverter;
import beyond.momentours.randomquestionanswer.command.domain.aggregate.entity.RQAnswer;
import beyond.momentours.randomquestionanswer.command.domain.repository.RQAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RQAnswerServiceImpl implements RQAnswerService {

    private final RQAnswerConverter rqAnswerConverter;
    private final RQAnswerRepository rqAnswerRepository;

    @Override
    public RQAnswerDTO createRQAnswer(RQAnswerDTO rqAnswerDTO, CustomUserDetails user) {

        Long memberId = user.getMemberId();
        rqAnswerDTO.setMemberId(memberId);
        rqAnswerDTO.setCreatedAt(LocalDateTime.now());
        rqAnswerDTO.setUpdatedAt(LocalDateTime.now());
        RQAnswer rqAnswer = rqAnswerConverter.dtoToEntity(rqAnswerDTO);

        rqAnswerRepository.save(rqAnswer);

        RQAnswerDTO createdRQAnswerDTO = rqAnswerConverter.entityToDTO(rqAnswer);

        return createdRQAnswerDTO;
    }
}
