package beyond.momentours.inquiry.query.controller;


import beyond.momentours.common.ResponseDTO;
import beyond.momentours.inquiry.query.dto.InquiryAndInquiryAnswerDTO;
import beyond.momentours.inquiry.query.service.InquiryQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController("queryInquiryController")
@RequestMapping("api/inquiry")
public class InquiryQueryController {

    private final InquiryQueryService inquiryQueryService;

    @Autowired
    public InquiryQueryController(InquiryQueryService inquiryQueryService) {
        this.inquiryQueryService = inquiryQueryService;
    }

    // 1. 모든 문의 조회 -> 문의에 대한 답변 함께 조회
    @GetMapping("/all")
    public ResponseDTO<List<InquiryAndInquiryAnswerDTO>> getAllInquiries() {
        List<InquiryAndInquiryAnswerDTO> response = inquiryQueryService.findAllInquiries();
        return ResponseDTO.ok(response);
    }

    // 2. 문의 id로 문의 조회 -> 문의에 대한 답변 함께 조회
    @GetMapping("/id")
    public ResponseDTO<InquiryAndInquiryAnswerDTO> getInquiryById(Long inquiryId) {
        InquiryAndInquiryAnswerDTO response = inquiryQueryService.findInquiryById(inquiryId);
        return ResponseDTO.ok(response);
    }

    // 3. 답변 여부로 문의 조회 -> 답변 true 들을 조회
    @GetMapping("/status")
    public ResponseDTO<List<InquiryAndInquiryAnswerDTO>> getInquiryByStatus(Boolean inquiryAnswerStatus) {
        List<InquiryAndInquiryAnswerDTO> response
                = inquiryQueryService.findInquiryAnswerStatusIsTrueOrFalse(inquiryAnswerStatus);
        return ResponseDTO.ok(response);
    }

    // 4. 작성자 id로 문의 조회
    @GetMapping("/member_id")
    public ResponseDTO<List<InquiryAndInquiryAnswerDTO>> getInquiryByMemberId(Long memberId) {
        List<InquiryAndInquiryAnswerDTO> response = inquiryQueryService.findInquiryAnswerByMemberId(memberId);
        return ResponseDTO.ok(response);
    }

    // 5. 답변 작성자 id로 문의 조회
    @GetMapping("/answer_member_id")
    public ResponseDTO<List<InquiryAndInquiryAnswerDTO>> findInquiryAnswerByAnswerMemberId(Long answerMemberId) {
        List<InquiryAndInquiryAnswerDTO> response = inquiryQueryService.findInquiryAnswerByAnswerMemberId(answerMemberId);
        return ResponseDTO.ok(response);
    }


}
