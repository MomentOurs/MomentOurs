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
                .answerCreatedAt(LocalDateTime.now())  // 생성 시간
                .answerUpdatedAt(LocalDateTime.now())  // 수정 시간
                .answerMemberId(memberId)  // 작성자 ID (파라미터로 받아온 회원 ID)
                .inquiryId(memberId)  // 작성자의 정보. 예시로 본인 ID 넣음
                .build();
    }

    // DTO -> VO (응답)
    public InquiryAnswerResponseVO dtoToVo(InquiryAnswerDTO dto) {
        return InquiryAnswerResponseVO.builder()
                .inquiryAnswerId(dto.getInquiryAnswerId()) // 답변 ID
                .inquiryAnswerContent(dto.getInquiryAnswerContent()) // 답변 내용
                .answerCreatedAt(dto.getAnswerCreatedAt()) // 생성 시간
                .answerUpdatedAt(dto.getAnswerUpdatedAt()) // 수정 시간
                .answerMemberId(dto.getAnswerMemberId()) // 작성자 ID
                .inquiryId(dto.getInquiryId()) // 문의 ID
                .build();
    }

    // DTO -> Entity 변환
    public InquiryAnswer dtoToEntity(InquiryAnswerDTO dto) {
        return InquiryAnswer.builder()
                .inquiryAnswerContent(dto.getInquiryAnswerContent()) // 답변 내용
                .answerCreatedAt(dto.getAnswerCreatedAt()) // 생성 시간
                .answerUpdatedAt(dto.getAnswerUpdatedAt()) // 수정 시간
                .answerMemberId(dto.getAnswerMemberId()) // 작성자 ID
                .inquiryId(dto.getInquiryId()) // 연관된 문의 ID
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
