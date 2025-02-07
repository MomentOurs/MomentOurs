package beyond.momentours.inquiry.query.service;

import beyond.momentours.inquiry.query.dto.InquiryAndInquiryAnswerDTO;
import beyond.momentours.inquiry.query.dto.InquiryListDTO;

import java.util.List;

public interface InquiryQueryService {

    // 1. 모든 문의 조회 -> 문의에 대한 답변 함께 조회
    List<InquiryAndInquiryAnswerDTO> findAllInquiries();

    // 2. 문의 id로 문의 조회 -> 문의에 대한 답변 함께 조회
    InquiryAndInquiryAnswerDTO findInquiryById(Long InquiryId);

    // 3. 답변 여부로 문의 조회 -> 답변 true 들을 조회
    List<InquiryAndInquiryAnswerDTO> findInquiryAnswerStatusIsTrueOrFalse(Boolean inquiryAnswerStatus);

    // 4. 작성자 id로 문의 조회
    List<InquiryAndInquiryAnswerDTO> findInquiryAnswerByMemberId(Long memberId);

    // 5. 답변 작성자 id로 문의 조회
    List<InquiryAndInquiryAnswerDTO> findInquiryAnswerByAnswerMemberId(Long answerMemberId);

    // 6. 키워드로 문의 조회
    List<InquiryAndInquiryAnswerDTO> findInquiryByKeyword(String keyword);

    // 7. 답변이 존재하는 문의 조회
    InquiryAndInquiryAnswerDTO findInquiryAnswerStatusIsTrue(Long inquiryId);

    // 8. 답변이 존재하지 않는 문의 조회
    InquiryAndInquiryAnswerDTO findInquiryAnswerStatusIsFalse(Long inquiryId);

    // 9. 문의 전체 목록 조회
    List<InquiryListDTO> findInquiryList();

    // 10. 답변 여부로 문의 목록 조회 true/false
    List<InquiryListDTO> findInquiryListAnswerStatusIsTrueOrFalse(Boolean inquiryAnswerStatus);

    // 11. 삭제된 문의 목록 조회
    List<InquiryListDTO> findInquiryListDeleted();
}
