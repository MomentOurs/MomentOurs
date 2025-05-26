package beyond.momentours.moment.common.converter;

import beyond.momentours.moment.command.application.dto.MomentDTO;
import beyond.momentours.moment.command.domain.aggregate.entity.Moment;
import beyond.momentours.moment.command.domain.aggregate.vo.request.RequestCreateMomentVO;
import org.springframework.stereotype.Component;

@Component
public class MomentConverter {

    public Moment fromDTOToEntity(MomentDTO dto, Long memberId, Long locationId) {
        return Moment.builder()
                .momentTitle(dto.getMomentTitle())
                .momentCategory(dto.getMomentCategory())
                .momentContent(dto.getMomentContent())
                .momentDisclosure(dto.isMomentDisclosure())
                .momentCommentStatus(dto.isMomentCommentStatus())
                .momentLike(dto.getMomentLike())
                .momentView(dto.getMomentView())
                .momentStatus(dto.isMomentStatus())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .locationId(locationId)
                .memberId(memberId)
                .build();
    }

    public MomentDTO fromEntityToDTO(Moment moment) {
        return MomentDTO.builder()
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

    public MomentDTO fromCreateVOToDTO(RequestCreateMomentVO createMomentVO) {
        return MomentDTO.builder()
                .momentTitle(createMomentVO.getMomentTitle())
                .momentCategory(createMomentVO.getMomentCategory())
                .momentContent(createMomentVO.getMomentContent())
                .momentCommentStatus(createMomentVO.isMomentCommentStatus())
                .momentLike(createMomentVO.getMomentLike())
                .momentView(createMomentVO.getMomentView())
                .locationId(createMomentVO.getLocationId())
                .locationName(createMomentVO.getLocationName())
                .latitude(createMomentVO.getLatitude())
                .longitude(createMomentVO.getLongitude())
                .build();
    }
}
