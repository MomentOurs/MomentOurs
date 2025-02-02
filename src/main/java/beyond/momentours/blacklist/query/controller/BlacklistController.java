package beyond.momentours.blacklist.query.controller;

import beyond.momentours.blacklist.query.service.BlacklistQueryService;
import beyond.momentours.blacklist.query.vo.response.ResponseBlacklistAll;
import beyond.momentours.common.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("queryBlacklistController")
@RequestMapping("api/blacklist")
public class BlacklistController {

    private final BlacklistQueryService blacklistQueryServicec;

    @Autowired
    public BlacklistController(BlacklistQueryService blacklistQueryServicec) {
        this.blacklistQueryServicec = blacklistQueryServicec;
    }

    @GetMapping("")
    private ResponseDTO<List<ResponseBlacklistAll>> getBlacklistAll() {
        List<ResponseBlacklistAll> response = blacklistQueryServicec.getBlacklistAll();
        return ResponseDTO.ok(response);
    }
}
