package beyond.momentours.pre_blacklist.command.application.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.pre_blacklist.command.application.dto.PreBlackListDTO;
import beyond.momentours.pre_blacklist.command.application.service.PreBlackListService;
import beyond.momentours.pre_blacklist.command.domain.vo.request.RequestPreBlackListStatus;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("commandPreBlackListController")
@RequestMapping("api/pre-blacklist")
public class PreBlackListController {

    private final PreBlackListService preBlackListService;


    @Autowired
    public PreBlackListController(PreBlackListService preBlacklistService) {
        this.preBlackListService = preBlacklistService;
    }

    /* 예비 블랙리스트 진행상태 변경 및 블랙리스트 등록 */
    @PatchMapping("status")
    public ResponseDTO<?> updateStatus(@RequestBody RequestPreBlackListStatus status) {
        PreBlackListDTO requestPreblackListDTO = PreBlackListDTO.builder()
                        .preBlackId(status.getPreBlackId())
                        .status(status.getStatus())
                        .build();
        preBlackListService.updateStatus(requestPreblackListDTO);
        return ResponseDTO.ok("예비 블랙리스트 진행상태가 성공적으로 변경되었습니다.");
    }

}
