package beyond.momentours.couplelog.command.application.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.couplelog.command.application.service.CommandCoupleLogService;
import beyond.momentours.couplelog.command.application.service.CursorTrackingService;
import beyond.momentours.couplelog.command.application.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/couple_log")
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

    // 수정 시작(PostMapping은 새로운 리소스 생성, 프로세스의 시작)
    @PostMapping("/{couplelogId}")
    public ResponseDTO<?> startUpdateCoupleLog(@PathVariable Long couplelogId, @RequestParam Long memberId) {
        // 편집 세션 시작
        cursorTrackingService.startUpdating(couplelogId, memberId);
        // websocket 연결 정보 설정
        webSocketService.registerEditor(couplelogId, memberId);

        return ResponseDTO.ok("웹소켓 연결 성공, 실시간 수정 시작");
    }
}
