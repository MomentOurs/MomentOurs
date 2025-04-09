package beyond.momentours.inquiry.command.application.mapper;


import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.inquiry.command.application.dto.InquiryDTO;
import beyond.momentours.inquiry.command.domain.aggregate.entity.Inquiry;
import beyond.momentours.inquiry.command.domain.aggregate.vo.request.InquiryCreateOrUpdateRequestVO;
import beyond.momentours.inquiry.command.domain.aggregate.vo.response.InquiryAnswerResponseVO;
import beyond.momentours.inquiry.command.domain.aggregate.vo.response.InquiryResponseVO;
import beyond.momentours.inquiry.command.domain.repository.InquiryRepository;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InquiryConverter {

    private final InquiryRepository inquiryRepository;

    public InquiryConverter(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }



    // VO -> DTO (요청)
    public InquiryDTO createVoToDto(InquiryCreateOrUpdateRequestVO requestVO, Long memberId, Long inquiryId) {
            // inquiryId가 null이 아닌 경우 -> 수정할 때 사용하는 경우
            return InquiryDTO.builder()
                    .inquiryTitle(requestVO.getInquiryTitle())
                    .inquiryContent(requestVO.getInquiryContent())
                    .inquiryMemberId(memberId)
                    .inquiryAnswerStatus(false)
                    .inquiryStatus(true)
                    .inquiryCreatedAt(LocalDateTime.now())
                    .inquiryUpdatedAt(LocalDateTime.now())
                    .inquiryId(inquiryId)
                    .build();

    }

    // VO -> DTO (요청)
    public InquiryDTO updateVoToDto(InquiryCreateOrUpdateRequestVO requestVO, Long memberId, Long inquiryId) {
        // inquiryId가 null이 아닌 경우 -> 수정할 때 사용하는 경우
        Inquiry existingInquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_INQUIRY));
        return InquiryDTO.builder()
                .inquiryTitle(requestVO.getInquiryTitle())
                .inquiryContent(requestVO.getInquiryContent())
                .inquiryMemberId(memberId)
                .inquiryAnswerStatus(false)
                .inquiryStatus(true)
                .inquiryCreatedAt(existingInquiry.getInquiryCreatedAt())
                .inquiryUpdatedAt(LocalDateTime.now())
                .inquiryId(inquiryId)
                .build();

    }



    // DTO -> VO (응답)
    public InquiryResponseVO dtoToVo(InquiryDTO dto) {
        return InquiryResponseVO.builder()
                .inquiryTitle(dto.getInquiryTitle())  // 제목
                .inquiryContent(dto.getInquiryContent()) // 내용
                .inquiryMemberId(dto.getInquiryMemberId()) // 작성자 ID (클라이언트에게 보낼 작성자 ID)
                .inquiryCreatedAt(dto.getInquiryCreatedAt()) // 등록 시간
                .inquiryUpdatedAt(dto.getInquiryUpdatedAt())
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
