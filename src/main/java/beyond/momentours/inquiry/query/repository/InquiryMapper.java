package beyond.momentours.inquiry.query.repository;


import beyond.momentours.inquiry.query.dto.InquiryAndInquiryAnswerDTO;
import beyond.momentours.inquiry.query.dto.InquiryDTO;
import beyond.momentours.inquiry.query.dto.InquiryListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InquiryMapper {

    // 1. 모든 문의 조회 -> 문의에 대한 답변 함께 조회
    List<InquiryAndInquiryAnswerDTO> selectAllInquiry();

    // 2. 문의 id로 문의 조회 -> 문의에 대한 답변 함께 조회
    InquiryAndInquiryAnswerDTO selectInquiryById(Long inquiryId);

    // 3. 답변 여부로 문의 조회 -> 답변 true 들을 조회
    List<InquiryAndInquiryAnswerDTO> selectInquiryAnswerStatusIsTrueOrFalse(Boolean inquiryAnswerStatus);

    // 4. 작성자 id로 문의 조회
    List<InquiryAndInquiryAnswerDTO> selectInquiryAnswerByMemberId(Long memberId);

    // 5. 답변 작성자 id로 문의 조회
    List<InquiryAndInquiryAnswerDTO> selectInquiryAnswerByAnswerMemberId(Long answerMemberId);

    // 6. 키워드로 문의 조회
    List<InquiryAndInquiryAnswerDTO> selectInquiryByKeyword(String keyword);

    // 7. 답변이 존재하는 문의 조회
    InquiryAndInquiryAnswerDTO selectInquiryAnswerStatusIsTrue(Long inquiryId);

    // 8. 답변이 존재하지 않는 문의 조회
    InquiryAndInquiryAnswerDTO selectInquiryAnswerStatusIsFalse(Long inquiryId);

    // 9. 문의 전체 목록 조회
    List<InquiryListDTO> selectAllInquiryList();

    // 10. 답변 여부로 문의 목록 조회 true/false
    List<InquiryListDTO> selectInquiryListAnswerStatusIsTrueOrFalse(Boolean inquiryAnswerStatus);

    // 11. 삭제된 문의 목록 조회
    List<InquiryListDTO> selectInquiryListDeleted();

}
