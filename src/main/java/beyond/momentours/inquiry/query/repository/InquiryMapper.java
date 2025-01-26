package beyond.momentours.inquiry.query.repository;


import beyond.momentours.inquiry.command.domain.aggregate.entity.Inquiry;
import beyond.momentours.inquiry.query.dto.InquiryAndInquiryAnswerDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InquiryMapper {

    // 1. 모든 문의 조회 -> 문의에 대한 답변 함께 조회
    List<InquiryAndInquiryAnswerDTO> selectAllInquiry();

    // 2. 문의 id로 문의 조회 -> 문의에 대한 답변 함께 조회
    InquiryAndInquiryAnswerDTO selectInquiryById(Long id);

    // 3. 답변 여부로 문의 조회 -> 답변 true 들을 조회
    List<InquiryAndInquiryAnswerDTO> selectInquiryAnswerStatusIsTrue(Boolean status);

    // 4. 답변 여부로 문의 조회 -> 답변 false 들을 조회
    List<InquiryAndInquiryAnswerDTO> selectInquiryAnswerStatusIsFalse(Boolean status);

    // 5. 작성자 id로 문의 조회
    List<InquiryAndInquiryAnswerDTO> selectInquiryAnswerByMemberId(Long memberId);

    // 6. 답변 작성자 id로 문의 조회
    List<InquiryAndInquiryAnswerDTO> selectInquiryAnswerByAnswerMemberId(Long answerMemberId);

    // 7. 키워드로 문의 조회
    List<InquiryAndInquiryAnswerDTO> selectInquiryAnswerByKeyword(String keyword);
}
