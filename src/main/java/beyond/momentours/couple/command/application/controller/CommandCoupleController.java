package beyond.momentours.couple.command.application.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.couple.command.application.dto.CoupleListDTO;
import beyond.momentours.couple.command.application.dto.CoupleProfileDTO;
import beyond.momentours.couple.command.application.dto.MatchingCodeDTO;
import beyond.momentours.couple.command.application.service.CommandCoupleService;
import beyond.momentours.couple.command.application.service.CoupleMatchingService;
import beyond.momentours.couple.command.domain.vo.CoupleListVO;
import beyond.momentours.couple.command.domain.vo.CoupleProfileRequestVO;
import beyond.momentours.couple.command.domain.vo.CoupleProfileResponseVO;
import beyond.momentours.couple.command.domain.vo.MatchingCodeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/couple")
public class CommandCoupleController {
    private final CommandCoupleService commandCoupleService;
    private final CoupleMatchingService coupleMatchingService;

    @Autowired
    public CommandCoupleController(CommandCoupleService commandCoupleService, CoupleMatchingService coupleMatchingService) {
        this.commandCoupleService = commandCoupleService;
        this.coupleMatchingService = coupleMatchingService;
    }

    // 매칭코드를 생성하는 controller method
    @PostMapping("/{userId}")
    public ResponseDTO<?> createMatchingCode(@PathVariable Long userId) {
        MatchingCodeDTO matchingCode = coupleMatchingService.createMatchingCode(userId);
        MatchingCodeVO matchingCodeVO = MatchingCodeVO.builder()
                .id(matchingCode.getId())
                .memberId(matchingCode.getMemberId())
                .createdAt(matchingCode.getCreatedAt())
                .matchingStatus(matchingCode.getMatchingStatus())
                .build();
        return ResponseDTO.ok(matchingCodeVO);
    }
    // 매칭코드로 인증하는 controller method
    @PostMapping("/authentication/{memberId}")
    public ResponseDTO<?> authenticateMatchingCode(@PathVariable Long memberId, @RequestBody MatchingCodeVO matchingCodeVO) {
       CoupleListDTO coupleListDTO =
               coupleMatchingService.authenticationMatchingCode(matchingCodeVO.getId(), memberId);
       CoupleListVO coupleListVO = CoupleListVO.builder()
               .coupleId(coupleListDTO.getCoupleId())
               .coupleName(coupleListDTO.getCoupleName())
               .couplePhoto(coupleListDTO.getCouplePhoto())
               .coupleStartDate(coupleListDTO.getCoupleStartDate())
               .coupleMatchingStatus(coupleListDTO.getCoupleMatchingStatus())
               .coupleStatus(coupleListDTO.getCoupleStatus())
               .memberId1(coupleListDTO.getMemberId1())
               .memberId2(coupleListDTO.getMemberId2())
               .build();
       return ResponseDTO.ok(coupleListVO);
    }

    // 커플 정보를 입력하는 메서드
    @PatchMapping("/profile")
    public ResponseDTO<?> editCoupleProfile(CoupleProfileRequestVO requestVO) {
        CoupleProfileDTO profileDTO = commandCoupleService.editProfile(requestVO);
        CoupleProfileResponseVO responseVO = CoupleProfileResponseVO.builder()
                .coupleId(profileDTO.getCoupleId())
                .coupleName(profileDTO.getCoupleName())
                .couplePhoto(profileDTO.getCouplePhoto())
                .coupleStartDate(profileDTO.getCoupleStartDate())
                .build();
        return ResponseDTO.ok(responseVO);
    }
}
