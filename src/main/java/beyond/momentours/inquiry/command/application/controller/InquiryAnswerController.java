package beyond.momentours.inquiry.command.application.controller;


import beyond.momentours.common.ResponseDTO;
import beyond.momentours.inquiry.command.application.dto.InquiryAnswerDTO;
import beyond.momentours.inquiry.command.application.mapper.InquiryAnswerConverter;
import beyond.momentours.inquiry.command.application.mapper.InquiryConverter;
import beyond.momentours.inquiry.command.application.service.InquiryAnswerCommandService;
import beyond.momentours.inquiry.command.application.service.InquiryCommandService;
import beyond.momentours.inquiry.command.domain.aggregate.vo.request.InquiryAnswerCreateOrUpdateVO;
import beyond.momentours.inquiry.command.domain.aggregate.vo.response.InquiryAnswerResponseVO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController("commandInquiryAnswerController")
@RequestMapping("api/inquiry-answer")
public class InquiryAnswerController {

    private final InquiryAnswerCommandService inquiryAnswerCommandService;
    private final InquiryAnswerConverter inquiryAnswerConverter;

    public InquiryAnswerController(InquiryAnswerCommandService inquiryAnswerCommandService, InquiryAnswerConverter inquiryAnswerConverter){
        this.inquiryAnswerCommandService = inquiryAnswerCommandService;
        this.inquiryAnswerConverter = inquiryAnswerConverter;
    }

    // 1. 문의 답변 등록
    @PostMapping("")
    public ResponseDTO<InquiryAnswerResponseVO> createInquiryAnswer(
            @RequestBody InquiryAnswerCreateOrUpdateVO requestVO,
            @AuthenticationPrincipal CustomUserDetails user
    ){
        Long answerMemberId = user.getMember().getMemberId();

        InquiryAnswerDTO requestDTO = inquiryAnswerConverter.voToDto(requestVO, answerMemberId);
        InquiryAnswerDTO responseDTO = inquiryAnswerCommandService.createInquiryAnswer(requestDTO);
        InquiryAnswerResponseVO responseVO = inquiryAnswerConverter.dtoToVo(responseDTO);

        return ResponseDTO.ok(responseVO);

    }


    // 2. 문의 답변 수정
    @PutMapping("/my/{inquiryAnswerId}")
    public ResponseDTO<InquiryAnswerResponseVO> updateInquiryAnswer(
            @PathVariable Long inquiryAnswerId,
            @RequestBody InquiryAnswerCreateOrUpdateVO requestVO,
            @AuthenticationPrincipal CustomUserDetails user
    ){
        Long answerMemberId = user.getMember().getMemberId();

        InquiryAnswerDTO requestDTO = inquiryAnswerConverter.voToDto(requestVO, answerMemberId);
        InquiryAnswerDTO responseDTO = inquiryAnswerCommandService.updateInquiryAnswer(requestDTO);
        InquiryAnswerResponseVO responseVO = inquiryAnswerConverter.dtoToVo(responseDTO);

        return ResponseDTO.ok(responseVO);
    }


    // 3. 문의 답변 삭제
    @DeleteMapping("/my/{inquiryAnswerId}")
    public void deleteInquiryAnswer(
            @PathVariable Long inquiryAnswerId,
            @AuthenticationPrincipal CustomUserDetails user){
        Long answerMemberId = user.getMember().getMemberId();
        inquiryAnswerCommandService.deleteInquiryAnswer(inquiryAnswerId, answerMemberId);

    }
}
