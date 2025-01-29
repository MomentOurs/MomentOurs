package beyond.momentours.inquiry.command.application.controller;


import beyond.momentours.announcement.command.domain.aggregate.dto.request.UpdateAnnouncementRequestDTO;
import beyond.momentours.common.ResponseDTO;
import beyond.momentours.inquiry.command.application.dto.InquiryDTO;
import beyond.momentours.inquiry.command.application.mapper.InquiryConverter;
import beyond.momentours.inquiry.command.application.service.InquiryCommandService;
import beyond.momentours.inquiry.command.domain.aggregate.entity.Inquiry;
import beyond.momentours.inquiry.command.domain.aggregate.vo.request.InquiryCreateOrUpdateRequestVO;
import beyond.momentours.inquiry.command.domain.aggregate.vo.response.InquiryResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import beyond.momentours.member.command.application.dto.CustomUserDetails;

@RestController("commandInquiryController")
@RequestMapping("api/inquiry")
public class InquiryController {

    private final InquiryCommandService inquiryCommandService;
    private final InquiryConverter inquiryConverter;


    public InquiryController(InquiryCommandService inquiryCommandService, InquiryConverter inquiryConverter){
        this.inquiryCommandService = inquiryCommandService;
        this.inquiryConverter = inquiryConverter;
    }

    // 1. 문의 등록
    @PostMapping("")
    public ResponseDTO<InquiryResponseVO> createInquiry(
            @RequestBody InquiryCreateOrUpdateRequestVO requestVO,
            CustomUserDetails user    // 로그인한 사용자 정보 + 요청 VO
    ) {
        Long memberId = user.getMember().getMemberId(); //로그인 사용자 정보

        InquiryDTO requestDTO = inquiryConverter.voToDto(requestVO, memberId,null);
        InquiryDTO responseDTO = inquiryCommandService.createInquiry(requestDTO);
        InquiryResponseVO responseVO = inquiryConverter.dtoToVo(responseDTO);

        return ResponseDTO.ok(responseVO);
    }


    // 2. 문의 수정
    @PutMapping("/my/{inquiryId}")
    public ResponseDTO<InquiryResponseVO> updateInquiry(
            @PathVariable Long inquiryId,
            @RequestBody InquiryCreateOrUpdateRequestVO requestVO,
            CustomUserDetails user
            ){
        Long memberId = user.getMember().getMemberId();

        InquiryDTO requestDTO = inquiryConverter.voToDto(requestVO, memberId, inquiryId);
        InquiryDTO responseDTO = inquiryCommandService.updateInquiry(requestDTO);
        InquiryResponseVO responseVO = inquiryConverter.dtoToVo(responseDTO);

        return ResponseDTO.ok(responseVO);

    }

    // 3. 문의 삭제
    @DeleteMapping("/my/{inquiryId}")
    public void deleteInquiry(
            @PathVariable Long inquiryId,
            @RequestBody CustomUserDetails user
    ){
        Long memberId = user.getMember().getMemberId();
        inquiryCommandService.deleteInquiry(inquiryId, memberId);
    }
}
