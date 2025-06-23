package beyond.momentours.moment.common.converter;

import beyond.momentours.moment.command.application.dto.MomentDTO;
import beyond.momentours.moment.command.domain.aggregate.entity.Moment;
import beyond.momentours.moment.command.domain.aggregate.vo.request.RequestCreateMomentVO;
import beyond.momentours.moment.command.domain.aggregate.vo.request.RequestUpdateMomentVO;
import beyond.momentours.moment.command.domain.aggregate.vo.response.ResponseCreateMomentVO;
import beyond.momentours.moment.command.domain.aggregate.vo.response.ResponseUpdateMomentVO;
import org.springframework.stereotype.Component;

@Component
public class MomentConverter {

    public Moment fromDTOToEntity(MomentDTO dto, Long memberId, Long coupleId, Long locationId) {
        return Moment.builder()
                .momentTitle(dto.getMomentTitle())
                .momentCategory(dto.getMomentCategory())
                .momentContent(dto.getMomentContent())
                .momentCertified(dto.isMomentCertified())
                .momentCommentStatus(dto.isMomentCommentStatus())
                .momentLike(dto.getMomentLike())
                .momentView(dto.getMomentView())
                .momentStatus(dto.isMomentStatus())
                .momentImageUrls(dto.getMomentImageUrls())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .locationId(locationId)
                .memberId(memberId)
                .coupleId(coupleId)
                .build();
    }

    public MomentDTO fromEntityToDTO(Moment moment) {
        return MomentDTO.builder()
                .momentId(moment.getMomentId())
                .momentTitle(moment.getMomentTitle())
                .momentCategory(moment.getMomentCategory())
                .momentContent(moment.getMomentContent())
                .momentCertified(moment.isMomentCertified())
                .momentCommentStatus(moment.isMomentCommentStatus())
                .momentLike(moment.getMomentLike())
                .momentView(moment.getMomentView())
                .momentStatus(moment.isMomentStatus())
                .momentImageUrls(moment.getMomentImageUrls())
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
                .momentImageUrls(createMomentVO.getMomentImageUrls())
                .locationId(createMomentVO.getLocationId())
                .locationName(createMomentVO.getLocationName())
                .latitude(createMomentVO.getLatitude())
                .longitude(createMomentVO.getLongitude())
                .build();
    }

    public MomentDTO fromUpdateVOToDTO(RequestUpdateMomentVO vo) {
        return MomentDTO.builder()
                .momentId(vo.getMomentId())
                .momentTitle(vo.getMomentTitle())
                .momentCategory(vo.getMomentCategory())
                .momentContent(vo.getMomentContent())
                .momentImageUrls(vo.getMomentImageUrls())
                .momentCommentStatus(vo.isMomentCommentStatus())
                .build();
    }

    public ResponseCreateMomentVO fromDTOToCreateVO(MomentDTO dto) {
        return ResponseCreateMomentVO.builder()
                .momentId(dto.getMomentId())
                .momentTitle(dto.getMomentTitle())
                .createdAt(dto.getCreatedAt())
                .momentImageUrls(dto.getMomentImageUrls())
                .build();
    }

    public ResponseUpdateMomentVO fromDTOToUpdateVO(MomentDTO dto) {
        return ResponseUpdateMomentVO.builder()
                .momentId(dto.getMomentId())
                .momentTitle(dto.getMomentTitle())
                .updatedAt(dto.getUpdatedAt())
                .momentImageUrls(dto.getMomentImageUrls())
                .build();
    }
}
