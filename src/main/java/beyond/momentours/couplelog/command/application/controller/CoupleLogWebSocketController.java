package beyond.momentours.couplelog.command.application.controller;

import beyond.momentours.couplelog.command.application.service.EditSessionService;
import beyond.momentours.couplelog.command.application.service.OperationalTransformService;
import beyond.momentours.couplelog.command.domain.aggregate.session.EditOperation;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@Slf4j
public class CoupleLogWebSocketController {
    private final EditSessionService editSessionService;
    private final OperationalTransformService otService;

    @Autowired
    public CoupleLogWebSocketController(EditSessionService editSessionService,
                                        OperationalTransformService otService) {
        this.editSessionService = editSessionService;
        this.otService = otService;
    }

    @MessageMapping("/couple-log/{couplelogId}/edit")
    @SendTo("/topic/couple-log/{couplelogId}")
    public EditOperation handleEdit(@DestinationVariable Long couplelogId,
                                    @Payload EditOperation clientOp,
                                    @Header("simpSessionAttributes") Map<String, Object> sessionAttributes) throws JsonProcessingException {
        log.debug("실시간 편집 요청: couplelogId={}, position={}, content={}",
                couplelogId, clientOp.getPosition(), clientOp.getContent());
        // 서버에 저장된 마지막 작업 조회
        EditOperation serverOp = editSessionService.getLastOperation(couplelogId);

        // 작업 변환
        EditOperation transformedOp = serverOp != null ?
                otService.transform(clientOp, serverOp) : clientOp;

        // 변환된 작업 저장
        editSessionService.saveOperation(couplelogId, transformedOp);

        return transformedOp;
    }
}
