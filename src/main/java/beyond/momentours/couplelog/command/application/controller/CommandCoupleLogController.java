package beyond.momentours.couplelog.command.application.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.couplelog.command.application.dto.CoupleLogDTO;
import beyond.momentours.couplelog.command.application.service.CommandCoupleLogService;
import beyond.momentours.couplelog.command.application.service.CursorTrackingService;
import beyond.momentours.couplelog.command.application.service.EditSessionService;
import beyond.momentours.couplelog.command.application.service.WebSocketService;
import beyond.momentours.couplelog.command.domain.vo.request.CoupleLogRequestVO;
import beyond.momentours.couplelog.command.domain.vo.response.EditSessionResponse;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/couple_log")
@Slf4j
public class CommandCoupleLogController {
    private final CommandCoupleLogService coupleLogService;
    private final EditSessionService editSessionService;

    @Autowired
    public CommandCoupleLogController(CommandCoupleLogService coupleLogService,
                                      EditSessionService editSessionService){
        this.coupleLogService = coupleLogService;
        this.editSessionService = editSessionService;
    }

    @PostMapping("/new-couple-log")
    public ResponseDTO<?> createNewCoupleLog(@AuthenticationPrincipal CustomUserDetails user,
                                             @RequestBody CoupleLogRequestVO requestVO) {
        log.info("새로운 커플로그 요청 controller 도착: {}", requestVO);
        coupleLogService.createNewCoupleLog(requestVO, user);
        return ResponseDTO.ok("새로운 커플로그 생성 성공");
    }

    // 수정 시작(PostMapping은 새로운 리소스 생성, 프로세스의 시작)
    @PostMapping("/{couplelogId}")
    public ResponseDTO<?> startEditing(@PathVariable Long couplelogId,
                                       @AuthenticationPrincipal CustomUserDetails user) {
        log.info("편집 모드 시작 요청: coupleLogId={}, memberId={}", couplelogId, user.getMember().getMemberId());
        // 편집 세션 시작 및 초기 데이터 반환
        editSessionService.startEditing(couplelogId, user.getMember().getMemberId());
        return ResponseDTO.ok("커플로그 편집 모드가 성공적으로 실행됨");
    }

    // 최종 저장
    @PatchMapping("/{couplelogId}")
    public ResponseDTO<?> updateCoupleLog(@PathVariable Long couplelogId,
                                          @AuthenticationPrincipal CustomUserDetails user,
                                          @RequestBody CoupleLogRequestVO requestVO) {
        log.info("커플로그 저장 요청: coupleLogId={}, memberId={}", couplelogId, user.getMember().getMemberId());
        CoupleLogDTO dto = coupleLogService.updateCoupleLog(couplelogId, user.getMember().getMemberId(), requestVO);
        editSessionService.endEditSession(couplelogId);
        return ResponseDTO.ok("커플로그 편집이 성공적으로 종료됨");
    }
}
