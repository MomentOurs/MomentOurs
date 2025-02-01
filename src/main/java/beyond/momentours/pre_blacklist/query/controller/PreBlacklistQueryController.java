package beyond.momentours.pre_blacklist.query.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.pre_blacklist.query.service.PreBlacklistQueryService;
import beyond.momentours.pre_blacklist.query.vo.response.ResponsePreBlacklistAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("queryPreBlacklistController")
@RequestMapping("api/pre-blacklist")
public class PreBlacklistQueryController {

    private final PreBlacklistQueryService preBlacklistQueryService;

    @Autowired
    public PreBlacklistQueryController(PreBlacklistQueryService preBlacklistQueryService) {
        this.preBlacklistQueryService = preBlacklistQueryService;
    }

    /* 회원별로 중복 없이 목록 조회 */
    @GetMapping("members")
    public ResponseDTO<List<ResponsePreBlacklistAll>> getPreBlacklistAll() {
        List<ResponsePreBlacklistAll> response = preBlacklistQueryService.getPreBlacklistAll();
        return ResponseDTO.ok(response);
    }
}
