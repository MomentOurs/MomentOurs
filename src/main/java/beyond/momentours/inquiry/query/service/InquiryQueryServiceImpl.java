package beyond.momentours.inquiry.query.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.inquiry.query.dto.InquiryAndInquiryAnswerDTO;
import beyond.momentours.inquiry.query.repository.InquiryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InquiryQueryServiceImpl implements InquiryQueryService {

    private final InquiryMapper inquiryMapper;
    private static final Logger log = LoggerFactory.getLogger(InquiryQueryServiceImpl.class);


    @Autowired
    public InquiryQueryServiceImpl(InquiryMapper inquiryMapper){
        this.inquiryMapper = inquiryMapper;
    }

    @Override
    // 1. 모든 문의 조회 -> 문의에 대한 답변 함께 조회
    public List<InquiryAndInquiryAnswerDTO> findAllInquiries(){
        // 문의가 존재하지 않으면 예외
        // 문의가 조회되지 않으면 예외
        try {
            List<InquiryAndInquiryAnswerDTO> inquiries = inquiryMapper.selectAllInquiry();
            if(inquiries == null || inquiries.isEmpty()){
                throw new RuntimeException("문의가 존재하지 않음");
            }
            return inquiries;
        }catch (Exception e){
            log.error("문의 조회 실패");
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 2. 문의 id로 문의 조회 -> 문의에 대한 답변 함께 조회
    public InquiryAndInquiryAnswerDTO findInquiryById(Long inquiryId){
        // 문의 id에 대한 문의가 존재하지 않으면 예외
        // 문의 id가 누락되면 예외
        // 문의가 조회되지 않으면 예외
        if(inquiryId == null){
            log.error("문의 id 누락됨");
            throw new IllegalArgumentException("문의 id 값을 넣어주세요");
        }
        try{
            InquiryAndInquiryAnswerDTO inquiry = inquiryMapper.selectInquiryById(inquiryId);
            if(inquiry == null){
                throw new RuntimeException("문의가 존재하지 않음");
            }
            return inquiry;
        } catch(Exception e){
            log.error("문의 조회 실패");
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 3. 답변 여부로 문의 조회 -> 답변 true 들을 조회
    public List<InquiryAndInquiryAnswerDTO> findInquiryAnswerStatusIsTrueOrFalse(Boolean inquiryAnswerStatus){
        // 답변 true 문의가 존재하지 않으면 예외
        // 조회 안 되면 예외 발생
        try {
            List<InquiryAndInquiryAnswerDTO> inquiries = inquiryMapper.selectInquiryAnswerStatusIsTrueOrFalse(inquiryAnswerStatus);
            if (inquiries == null || inquiries.isEmpty()) {
                throw new RuntimeException("현재 답변 상태에 대한 문의가 존재하지 않음");
            }
            return inquiries;
        } catch (Exception e){
            log.error("문의 조회 실패");
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 4. 작성자 id로 문의 조회
    public List<InquiryAndInquiryAnswerDTO> findInquiryAnswerByMemberId(Long memberId){
        // 작성자 id가 존재하지 않으면 예외
        // 문의가 존재하지 않으면 예외
        // 문의가 조회되지 않으면 예외
        if(memberId == null){
            throw new IllegalArgumentException("memberId 값을 넣어주새요");
        }
        try{
            List<InquiryAndInquiryAnswerDTO> inquiries = inquiryMapper.selectInquiryAnswerByMemberId(memberId);
            if(inquiries == null || inquiries.isEmpty()) {
                throw new RuntimeException("해당 작성자 id에 대한 문의가 존재하지 않음");
            }
            return inquiries;
        }catch(Exception e){
            log.error("문의 조회 실패");
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 5. 답변 작성자 id로 문의 조회
    public List<InquiryAndInquiryAnswerDTO> findInquiryAnswerByAnswerMemberId(Long answerMemberId){
        // 답변 작성자 id가 존재하지 않으면 예외
        // 문의가 조회되지 않으면 예외
        if(answerMemberId == null){
            log.error("답변 작성자 id 값 누락");
            throw new IllegalArgumentException("답변 작성자 id 값을 넣어주세요");
        }
        try{
            List<InquiryAndInquiryAnswerDTO> inquiries = inquiryMapper.selectInquiryAnswerByAnswerMemberId(answerMemberId);
            if(inquiries == null || inquiries.isEmpty()) {
               throw new RuntimeException("해당 답변 작성자 id에 대한 문의가 존재하지 않음");
            }
            return inquiries;
        }catch(Exception e){
            log.error("문의 조회 실패");
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 6. 키워드로 문의 조회
    public List<InquiryAndInquiryAnswerDTO> findInquiryByKeyword(String keyword){
        // 키워드가 존재하지 않으면 예외
        // 문의가 조회되지 않으면 예외
        if(keyword == null){
            log.error("키워드 값 누락");
            throw new IllegalArgumentException("키워드 값을 넣어주세요");
        } try{
            List<InquiryAndInquiryAnswerDTO> inquiries = inquiryMapper.selectInquiryByKeyword(keyword);
            if(inquiries == null || inquiries.isEmpty()) {
                throw new RuntimeException("해당 키워드와 일치하는 문의가 없음");
            }
            return inquiries;
        } catch (Exception e){
            log.error("문의 조회 실패");
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


}
