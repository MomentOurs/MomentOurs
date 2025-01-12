package beyond.momentours.moment.command.application.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.moment.command.application.dto.RequestMomentDTO;
import beyond.momentours.moment.command.application.dto.ResponseMomentDTO;
import beyond.momentours.moment.command.application.service.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseDTO<?> createMoment(@RequestBody RequestMomentDTO requestMomentDTO) {
        ResponseMomentDTO responseMomentDTO = momentService.createMoment(requestMomentDTO);
        return ResponseDTO.ok(responseMomentDTO);
    }

}
