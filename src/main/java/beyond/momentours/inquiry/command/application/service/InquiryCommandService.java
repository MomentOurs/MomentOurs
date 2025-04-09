package beyond.momentours.inquiry.command.application.service;

import beyond.momentours.inquiry.command.application.dto.InquiryDTO;
import beyond.momentours.inquiry.command.domain.aggregate.vo.request.InquiryCreateOrUpdateRequestVO;

public interface InquiryCommandService {

    // 1. 문의 등록
    public InquiryDTO createInquiry(InquiryDTO inquiryDTO);

    // 2. 문의 수정
    public InquiryDTO updateInquiry(InquiryDTO inquiryDTO);

    // 3. 문의 삭제
    public void deleteInquiry(Long inquiryId, Long memberId);
}
