package beyond.momentours.moment.query.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.moment.query.service.MomentQueryService;
import beyond.momentours.moment.query.vo.ResponseMomentDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/moment")
@RequiredArgsConstructor
public class MomentQueryController {

    private final MomentQueryService momentQueryService;

    @GetMapping("/{momentId}")
    public ResponseDTO<ResponseMomentDetailVO> getMomentDetail(@PathVariable Long momentId) {
        ResponseMomentDetailVO result = momentQueryService.getMomentDetail(momentId);
        return ResponseDTO.ok(result);
    }
}
