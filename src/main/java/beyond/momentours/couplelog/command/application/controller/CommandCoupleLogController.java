package beyond.momentours.couplelog.command.application.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.couplelog.command.application.service.CommandCoupleLogService;
import beyond.momentours.couplelog.command.application.service.CursorTrackingService;
import beyond.momentours.couplelog.command.application.service.WebSocketService;
import beyond.momentours.couplelog.command.domain.vo.request.CoupleLogRequestVO;
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
    private final CursorTrackingService cursorTrackingService;
    private final WebSocketService webSocketService;

    @Autowired
    public CommandCoupleLogController(CommandCoupleLogService coupleLogService,
                                      CursorTrackingService cursorTrackingService,
                                      WebSocketService webSocketService){
        this.coupleLogService = coupleLogService;
        this.cursorTrackingService = cursorTrackingService;
        this.webSocketService = webSocketService;
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
    public ResponseDTO<?> startUpdateCoupleLog(@PathVariable Long couplelogId,
                                               @AuthenticationPrincipal CustomUserDetails user) {
        // 편집 세션 시작
        cursorTrackingService.startUpdating(couplelogId, user);
        // websocket 연결 정보 설정
        webSocketService.registerEditor(couplelogId, user);

        return ResponseDTO.ok("웹소켓 연결 성공, 실시간 수정 시작");
    }

    @PatchMapping("/{couplelogId}")
    public ResponseDTO<?> updateCoupleLog(@PathVariable Long couplelogId,
                                          @AuthenticationPrincipal CustomUserDetails user,
                                          @RequestBody CoupleLogRequestVO requestCoupleLogVO) {
        return null;
    }
}
