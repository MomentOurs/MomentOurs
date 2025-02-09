package beyond.momentours.inquiry.query.controller;


import beyond.momentours.common.ResponseDTO;
import beyond.momentours.inquiry.query.dto.InquiryAndInquiryAnswerDTO;
import beyond.momentours.inquiry.query.dto.InquiryListDTO;
import beyond.momentours.inquiry.query.service.InquiryQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/id/{inquiryId}")
    public ResponseDTO<InquiryAndInquiryAnswerDTO> getInquiryById(@PathVariable Long inquiryId) {
        InquiryAndInquiryAnswerDTO response = inquiryQueryService.findInquiryById(inquiryId);
        return ResponseDTO.ok(response);
    }

    // 3. 답변 여부로 문의 조회 -> 답변 true 들을 조회
    @GetMapping("/answer/status")
    public ResponseDTO<List<InquiryAndInquiryAnswerDTO>> getInquiryByStatus(@RequestParam Boolean status) {
        List<InquiryAndInquiryAnswerDTO> response
                = inquiryQueryService.findInquiryAnswerStatusIsTrueOrFalse(status);
        return ResponseDTO.ok(response);
    }

    // 4. 작성자 id로 문의 조회
    @GetMapping("/member-id")
    public ResponseDTO<List<InquiryAndInquiryAnswerDTO>> getInquiryByMemberId(@RequestParam("member-id") Long memberId) {
        List<InquiryAndInquiryAnswerDTO> response = inquiryQueryService.findInquiryAnswerByMemberId(memberId);
        return ResponseDTO.ok(response);
    }

    // 5. 답변 작성자 id로 문의 조회
    @GetMapping("/answer-member-id")
    public ResponseDTO<List<InquiryAndInquiryAnswerDTO>> getInquiryAnswerByAnswerMemberId(
            @RequestParam("answer-member-id") Long answerMemberId)
    {
        List<InquiryAndInquiryAnswerDTO> response = inquiryQueryService.findInquiryAnswerByAnswerMemberId(answerMemberId);
        return ResponseDTO.ok(response);
    }

    // 7. 답변이 존재하는 문의 조회
    @GetMapping("/answer/status/true/{inquiryId}")
    public ResponseDTO<InquiryAndInquiryAnswerDTO> getInquiryAnswerStatusIsTrue(@PathVariable Long inquiryId)
    {
        InquiryAndInquiryAnswerDTO response = inquiryQueryService.findInquiryAnswerStatusIsTrue(inquiryId);
        return ResponseDTO.ok(response);
    }

    // 8. 답변이 존재하지 않는 문의 조회
    @GetMapping("/answer/status/false/{inquiryId}")
    public ResponseDTO<InquiryAndInquiryAnswerDTO> getInquiryAnswerStatusIsFalse(@PathVariable Long inquiryId)
    {
        InquiryAndInquiryAnswerDTO response = inquiryQueryService.findInquiryAnswerStatusIsFalse(inquiryId);
        return ResponseDTO.ok(response);
    }

    // 9. 문의 전체 목록 조회
    @GetMapping("/list/all")
    public ResponseDTO<List<InquiryListDTO>> getInquiryList(){
        List<InquiryListDTO> response = inquiryQueryService.findInquiryList();
        return ResponseDTO.ok(response);
    }

    // 10. 답변 여부로 문의 목록 조회 true/false
    @GetMapping("/list/status")
    public ResponseDTO<List<InquiryListDTO>> getInquiryListByStatus(@RequestParam Boolean status) {
        List<InquiryListDTO> response = inquiryQueryService.findInquiryListAnswerStatusIsTrueOrFalse(status);
        return ResponseDTO.ok(response);
    }

    // 11. 삭제된 문의 목록 조회
    @GetMapping("/list/deleted")
    public ResponseDTO<List<InquiryListDTO>> getInquiryListDeleted(){
        List<InquiryListDTO> response = inquiryQueryService.findInquiryListDeleted();
        return ResponseDTO.ok(response);
    }


}
