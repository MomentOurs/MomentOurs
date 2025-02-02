package beyond.momentours.pre_blacklist.command.application.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.pre_blacklist.command.application.dto.PreBlacklistDTO;
import beyond.momentours.pre_blacklist.command.application.service.PreBlacklistService;
import beyond.momentours.pre_blacklist.command.domain.vo.request.RequestPreBlacklistStatus;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("commandPreBlacklistController")
@RequestMapping("api/pre-blacklist")
public class PreBlacklistController {

    private final PreBlacklistService preBlacklistService;


    @Autowired
    public PreBlacklistController(PreBlacklistService preBlacklistService) {
        this.preBlacklistService = preBlacklistService;
    }

    /* 예비 블랙리스트 진행상태 변경 및 블랙리스트 등록 */
    @PatchMapping("status")
    public ResponseDTO<?> updateStatus(@RequestBody RequestPreBlacklistStatus status) {
        PreBlacklistDTO requestPreblacklistDTO = PreBlacklistDTO.builder()
                        .preBlackId(status.getPreBlackId())
                        .status(status.getStatus())
                        .build();
        preBlacklistService.updateStatus(requestPreblacklistDTO);
        return ResponseDTO.ok("예비 블랙리스트 진행상태가 성공적으로 변경되었습니다.");
    }

}
