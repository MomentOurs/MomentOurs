package beyond.momentours.couple.command.application.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.couple.command.application.dto.CoupleListDTO;
import beyond.momentours.couple.command.application.dto.CoupleProfileDTO;
import beyond.momentours.couple.command.application.dto.MatchingCodeDTO;
import beyond.momentours.couple.command.application.mapper.CodeConverter;
import beyond.momentours.couple.command.application.mapper.CoupleConverter;
import beyond.momentours.couple.command.application.service.CommandCoupleService;
import beyond.momentours.couple.command.application.service.CoupleMatchingService;
import beyond.momentours.couple.command.domain.vo.response.CoupleListResponseVO;
import beyond.momentours.couple.command.domain.vo.request.CoupleProfileRequestVO;
import beyond.momentours.couple.command.domain.vo.response.CoupleProfileResponseVO;
import beyond.momentours.couple.command.domain.vo.response.MatchingCodeResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/couple")
public class CommandCoupleController {
    private final CommandCoupleService commandCoupleService;
    private final CoupleMatchingService coupleMatchingService;
    private final CodeConverter codeConverter;
    private final CoupleConverter coupleConverter;

    @Autowired
    public CommandCoupleController(CommandCoupleService commandCoupleService,
                                   CoupleMatchingService coupleMatchingService,
                                   CodeConverter codeConverter,
                                   CoupleConverter coupleConverter) {
        this.commandCoupleService = commandCoupleService;
        this.coupleMatchingService = coupleMatchingService;
        this.codeConverter = codeConverter;
        this.coupleConverter = coupleConverter;
    }

    // 매칭코드를 생성하는 controller method
    @PostMapping("/{userId}")
    public ResponseDTO<?> createMatchingCode(@PathVariable Long userId) {
        MatchingCodeDTO codeDTO = coupleMatchingService.createMatchingCode(userId);
        MatchingCodeResponseVO matchingCodeVO = codeConverter.fromDtoToResponseVO(codeDTO);
        return ResponseDTO.ok(matchingCodeVO);
    }

    // 매칭코드로 인증하는 controller method
    @PostMapping("/authentication/{memberId}")
    public ResponseDTO<?> authenticateMatchingCode(@PathVariable Long memberId, @RequestBody MatchingCodeResponseVO matchingCodeVO) {
       CoupleListDTO coupleDTO = coupleMatchingService.authenticationMatchingCode(matchingCodeVO.getId(), memberId);
       CoupleListResponseVO responseVO = coupleConverter.fromDtoToCoupleVO(coupleDTO);
       return ResponseDTO.ok(responseVO);
    }

    // 커플 정보를 입력하는 메서드
    @PatchMapping("/profile")
    public ResponseDTO<?> editCoupleProfile(CoupleProfileRequestVO requestVO) {
        CoupleProfileDTO profileDTO = commandCoupleService.updateProfile(requestVO);
        CoupleProfileResponseVO responseVO = coupleConverter.fromDtoToCoupleVO(profileDTO);
        return ResponseDTO.ok(responseVO);
    }

    // 커플 삭제하는 메서드
    @PostMapping("/soft-delete/{coupleId}")
    public ResponseDTO<?> deleteCouple(@PathVariable Long coupleId) {
        CoupleListDTO coupleDTO = commandCoupleService.deleteCouple(coupleId);
        return ResponseDTO.ok("성공적으로 삭제되었습니다.");
    }
}
