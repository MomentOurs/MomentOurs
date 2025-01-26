package beyond.momentours.moment.command.application.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.moment.command.application.dto.RequestMomentDTO;
import beyond.momentours.moment.command.application.dto.ResponseMomentDTO;
import beyond.momentours.moment.command.application.service.MomentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("commandMomentController")
@RequestMapping("api/moment")
public class MomentController {

    private final MomentService momentService;

    @Autowired
    public MomentController(MomentService momentService) {
        this.momentService = momentService;
    }

    // 추억 등록
    @PostMapping
    public ResponseDTO<?> createMoment(@RequestBody RequestMomentDTO requestMomentDTO,
                                       @AuthenticationPrincipal CustomUserDetails user) {
        log.info("등록 요청 데이터 : {}", requestMomentDTO);
        ResponseMomentDTO responseMomentDTO = momentService.createMoment(requestMomentDTO, user);
        return ResponseDTO.ok(responseMomentDTO);
    }

}
