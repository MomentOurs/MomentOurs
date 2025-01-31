package beyond.momentours.moment.command.application.mapper;

import beyond.momentours.moment.command.application.dto.RequestMomentDTO;
import beyond.momentours.moment.command.application.dto.ResponseMomentDTO;
import beyond.momentours.moment.command.domain.aggregate.entity.Moment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MomentConverter {

    public Moment fromDTOToEntity(RequestMomentDTO dto, Long memberId, Long locationId) {
        return Moment.builder()
                .momentTitle(dto.getMomentTitle())
                .momentCategory(dto.getMomentCategory())
                .momentContent(dto.getMomentContent())
                .momentDisclosure(dto.isMomentDisclosure())
                .momentCommentStatus(dto.isMomentCommentStatus())
                .momentLike(0)
                .momentView(0)
                .momentStatus(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .locationId(locationId)
                .memberId(memberId)
                .build();
    }

    public ResponseMomentDTO fromEntityToDTO(Moment moment) {
        return ResponseMomentDTO.builder()
                .momentId(moment.getMomentId())
                .momentTitle(moment.getMomentTitle())
                .momentCategory(moment.getMomentCategory())
                .momentContent(moment.getMomentContent())
                .momentDisclosure(moment.isMomentDisclosure())
                .momentCommentStatus(moment.isMomentCommentStatus())
                .momentLike(moment.getMomentLike())
                .momentView(moment.getMomentView())
                .momentStatus(moment.isMomentStatus())
                .createdAt(moment.getCreatedAt())
                .updatedAt(moment.getUpdatedAt())
                .locationId(moment.getLocationId())
                .memberId(moment.getMemberId())
                .build();
    }
}
