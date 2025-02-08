package beyond.momentours.inquiry.command.application.mapper;

import beyond.momentours.inquiry.command.application.dto.InquiryAnswerDTO;
import beyond.momentours.inquiry.command.application.dto.InquiryDTO;
import beyond.momentours.inquiry.command.domain.aggregate.entity.Inquiry;
import beyond.momentours.inquiry.command.domain.aggregate.entity.InquiryAnswer;
import beyond.momentours.inquiry.command.domain.aggregate.vo.request.InquiryAnswerCreateOrUpdateVO;
import beyond.momentours.inquiry.command.domain.aggregate.vo.response.InquiryAnswerResponseVO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class InquiryAnswerConverter {

    // VO -> DTO (요청)
    public InquiryAnswerDTO voToDto(InquiryAnswerCreateOrUpdateVO requestVO, Long memberId) {
        // InquiryAnswerDTO는 답변 내용, 작성자 정보 등은 별도로 처리할 수 있는 정보를 받도록 함
        return InquiryAnswerDTO.builder()
                .inquiryAnswerContent(requestVO.getInquiryAnswerContent())
                .answerCreatedAt(LocalDateTime.now())
                .answerUpdatedAt(LocalDateTime.now())
                .answerMemberId(memberId)
                .inquiryId(memberId)
                .build();
    }

    // DTO -> VO (응답)
    public InquiryAnswerResponseVO dtoToVo(InquiryAnswerDTO dto) {
        return InquiryAnswerResponseVO.builder()
                .inquiryAnswerId(dto.getInquiryAnswerId())
                .inquiryAnswerContent(dto.getInquiryAnswerContent())
                .answerCreatedAt(dto.getAnswerCreatedAt())
                .answerUpdatedAt(dto.getAnswerUpdatedAt())
                .answerMemberId(dto.getAnswerMemberId())
                .inquiryId(dto.getInquiryId())
                .build();
    }

    // DTO -> Entity 변환
    public InquiryAnswer dtoToEntity(InquiryAnswerDTO dto) {
        return InquiryAnswer.builder()
                .inquiryAnswerContent(dto.getInquiryAnswerContent())
                .answerCreatedAt(dto.getAnswerCreatedAt())
                .answerUpdatedAt(dto.getAnswerUpdatedAt())
                .answerMemberId(dto.getAnswerMemberId())
                .inquiryId(dto.getInquiryId())
                .build();
    }

    // Entity -> DTO 변환
    public InquiryAnswerDTO entityToDto(InquiryAnswer entity) {
        return InquiryAnswerDTO.builder()
                .inquiryAnswerId(entity.getInquiryAnswerId())
                .inquiryAnswerContent(entity.getInquiryAnswerContent())
                .answerCreatedAt(entity.getAnswerCreatedAt())
                .answerUpdatedAt(entity.getAnswerUpdatedAt())
                .answerMemberId(entity.getAnswerMemberId())
                .inquiryId(entity.getInquiryId())
                .build();
    }
}
