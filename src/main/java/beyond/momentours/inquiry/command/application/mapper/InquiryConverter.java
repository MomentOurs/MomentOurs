package beyond.momentours.inquiry.command.application.mapper;


import beyond.momentours.inquiry.command.application.dto.InquiryDTO;
import beyond.momentours.inquiry.command.domain.aggregate.entity.Inquiry;
import beyond.momentours.inquiry.command.domain.aggregate.vo.request.InquiryCreateOrUpdateRequestVO;
import beyond.momentours.inquiry.command.domain.aggregate.vo.response.InquiryAnswerResponseVO;
import beyond.momentours.inquiry.command.domain.aggregate.vo.response.InquiryResponseVO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InquiryConverter {



    // VO -> DTO (요청)
    public InquiryDTO voToDto(InquiryCreateOrUpdateRequestVO requestVO, Long memberId) {
        return InquiryDTO.builder()
                .inquiryTitle(requestVO.getInquiryTitle())
                .inquiryContent(requestVO.getInquiryContent())
                .inquiryMemberId(memberId) // 회원 정보 추가
                .inquiryAnswerStatus(false)
                .inquiryStatus(true)
                .inquiryCreatedAt(LocalDateTime.now())
                .inquiryUpdatedAt(LocalDateTime.now())
                .build();
    }

    // DTO -> VO (응답)
    public InquiryResponseVO dtoToVo(InquiryDTO dto) {
        return InquiryResponseVO.builder()
                .inquiryTitle(dto.getInquiryTitle())  // 제목
                .inquiryContent(dto.getInquiryContent()) // 내용
                .inquiryMemberId(dto.getInquiryMemberId()) // 작성자 ID (클라이언트에게 보낼 작성자 ID)
                .inquiryCreatedAt(dto.getInquiryCreatedAt()) // 등록 시간
                .inquiryAnswerStatus(dto.getInquiryAnswerStatus()) // 답변 여부
                .build();
    }


    // DTO -> Entity 변환
    public Inquiry dtoToEntity(InquiryDTO dto) {
        return Inquiry.builder()
                .inquiryId(dto.getInquiryId())
                .inquiryTitle(dto.getInquiryTitle())
                .inquiryContent(dto.getInquiryContent())
                .inquiryAnswerStatus(dto.getInquiryAnswerStatus())
                .inquiryStatus(dto.getInquiryStatus())
                .inquiryCreatedAt(dto.getInquiryCreatedAt())
                .inquiryUpdatedAt(dto.getInquiryUpdatedAt())
                .inquiryMemberId(dto.getInquiryMemberId())
                .build();
    }

    // Entity -> DTO 변환
    public InquiryDTO entityToDto(Inquiry entity) {
        return InquiryDTO.builder()
                .inquiryId(entity.getInquiryId())
                .inquiryTitle(entity.getInquiryTitle())
                .inquiryContent(entity.getInquiryContent())
                .inquiryAnswerStatus(entity.getInquiryAnswerStatus())
                .inquiryStatus(entity.getInquiryStatus())
                .inquiryCreatedAt(entity.getInquiryCreatedAt())
                .inquiryUpdatedAt(entity.getInquiryUpdatedAt())
                .inquiryMemberId(entity.getInquiryMemberId())
                .build();
    }
}
