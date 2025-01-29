package beyond.momentours.inquiry.command.application.service;

import beyond.momentours.inquiry.command.application.dto.InquiryAnswerDTO;

public interface InquiryAnswerCommandService {

    // 1. 문의 답변 등록
    public InquiryAnswerDTO createInquiryAnswer(InquiryAnswerDTO inquiryAnswerDTO);

    // 2. 문의 답변 수정
    public InquiryAnswerDTO updateInquiryAnswer(InquiryAnswerDTO inquiryAnswerDTO);

    // 3. 문의 답변 삭제
    public void deleteInquiryAnswer(Long inquiryAnswerId, Long answerMemberId);
}
