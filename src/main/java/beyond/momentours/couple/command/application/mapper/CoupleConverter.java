package beyond.momentours.couple.command.application.mapper;

import beyond.momentours.couple.command.application.dto.CoupleListDTO;
import beyond.momentours.couple.command.application.dto.CoupleProfileDTO;
import beyond.momentours.couple.command.domain.aggregate.entity.CoupleList;
import beyond.momentours.couple.command.domain.vo.response.CoupleListResponseVO;
import beyond.momentours.couple.command.domain.vo.response.CoupleProfileResponseVO;
import org.springframework.stereotype.Component;

@Component
public class CoupleConverter {
    public CoupleListDTO fromEntityToCoupleDTO(CoupleList couple) {
        return CoupleListDTO.builder()
                .coupleId(couple.getCoupleId())
                .coupleName(couple.getCoupleName())
                .couplePhoto(couple.getCouplePhoto())
                .coupleStartDate(couple.getCoupleStartDate())
                .coupleMatchingStatus(couple.getCoupleMatchingStatus())
                .coupleStatus(couple.getCoupleStatus())
                .memberId1(couple.getMemberId1())
                .memberId2(couple.getMemberId2())
                .build();
    }

    public CoupleProfileDTO fromEntityToProfileDTO(CoupleList couple) {
        return CoupleProfileDTO.builder()
                .coupleId(couple.getCoupleId())
                .coupleName(couple.getCoupleName())
                .couplePhoto(couple.getCouplePhoto())
                .coupleStartDate(couple.getCoupleStartDate())
                .build();
    }

    public CoupleListResponseVO fromDtoToCoupleVO(CoupleListDTO dto) {
        return CoupleListResponseVO.builder()
                .coupleId(dto.getCoupleId())
                .coupleName(dto.getCoupleName())
                .couplePhoto(dto.getCouplePhoto())
                .coupleStartDate(dto.getCoupleStartDate())
                .coupleMatchingStatus(dto.getCoupleMatchingStatus())
                .coupleStatus(dto.getCoupleStatus())
                .memberId1(dto.getMemberId1())
                .memberId2(dto.getMemberId2())
                .build();
    }

    public CoupleProfileResponseVO fromDtoToCoupleVO(CoupleProfileDTO dto) {
        return CoupleProfileResponseVO.builder()
                .coupleId(dto.getCoupleId())
                .coupleName(dto.getCoupleName())
                .couplePhoto(dto.getCouplePhoto())
                .coupleStartDate(dto.getCoupleStartDate())
                .build();
    }
}
