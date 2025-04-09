package beyond.momentours.inquiry.command.application.mapper;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.inquiry.command.application.dto.InquiryAnswerDTO;
import beyond.momentours.inquiry.command.application.dto.InquiryDTO;
import beyond.momentours.inquiry.command.domain.aggregate.entity.Inquiry;
import beyond.momentours.inquiry.command.domain.aggregate.entity.InquiryAnswer;
import beyond.momentours.inquiry.command.domain.aggregate.vo.request.InquiryAnswerCreateOrUpdateVO;
import beyond.momentours.inquiry.command.domain.aggregate.vo.response.InquiryAnswerResponseVO;
import beyond.momentours.inquiry.command.domain.repository.InquiryAnswerRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class InquiryAnswerConverter {

    public final InquiryAnswerRepository inquiryAnswerRepository;

    public InquiryAnswerConverter(InquiryAnswerRepository inquiryAnswerRepository) {
        this.inquiryAnswerRepository = inquiryAnswerRepository;
    }

    // VO -> DTO (요청)
    // 문의 답변 등록
    public InquiryAnswerDTO createVoToDto(InquiryAnswerCreateOrUpdateVO requestVO, Long answerMemberId, Long inquiryId) {
        return InquiryAnswerDTO.builder()
                .inquiryAnswerContent(requestVO.getInquiryAnswerContent())
                .answerCreatedAt(LocalDateTime.now())
                .answerUpdatedAt(LocalDateTime.now())
                .answerMemberId(answerMemberId)
                .inquiryId(inquiryId)
                .build();
    }

    // VO -> DTO (요청)
    // 문의 답변 수정
    public InquiryAnswerDTO updateVoToDto(InquiryAnswerCreateOrUpdateVO requestVO, Long answerMemberId, Long inquiryId) {

        InquiryAnswer existingInquiryAnswer = inquiryAnswerRepository.findByInquiryId(inquiryId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_INQUIRY));

        return InquiryAnswerDTO.builder()
                .inquiryAnswerId(existingInquiryAnswer.getInquiryAnswerId())
                .inquiryAnswerContent(requestVO.getInquiryAnswerContent())
                .answerCreatedAt(existingInquiryAnswer.getAnswerCreatedAt())
                .answerUpdatedAt(LocalDateTime.now())
                .answerMemberId(answerMemberId)
                .inquiryId(inquiryId)
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
                .inquiryAnswerId(dto.getInquiryAnswerId())
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
