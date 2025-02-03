package beyond.momentours.pre_blacklist.query.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.pre_blacklist.query.service.PreBlackListQueryService;
import beyond.momentours.pre_blacklist.query.vo.response.ResponsePreBlackListAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("queryPreBlacklistController")
@RequestMapping("api/pre-blacklist")
public class PreBlackListQueryController {

    private final PreBlackListQueryService preBlackListQueryService;

    @Autowired
    public PreBlackListQueryController(PreBlackListQueryService preBlackListQueryService) {
        this.preBlackListQueryService = preBlackListQueryService;
    }

    /* 회원별로 중복 없이 목록 조회 */
    @GetMapping("members")
    public ResponseDTO<List<ResponsePreBlackListAll>> getPreBlacklistAll() {
        List<ResponsePreBlackListAll> response = preBlackListQueryService.getPreBlacklistAll();
        return ResponseDTO.ok(response);
    }
}
