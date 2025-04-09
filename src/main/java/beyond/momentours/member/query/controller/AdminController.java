package beyond.momentours.member.query.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.member.query.service.AdminQueryService;
import beyond.momentours.member.query.vo.response.ResponseLoginHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("queryAdminController")
@RequestMapping("api/admin")
public class AdminController {

    private final AdminQueryService adminQueryService;

    @Autowired
    public AdminController(AdminQueryService adminQueryService) {
        this.adminQueryService = adminQueryService;
    }

    /* 로그인 IP 이력 조회 */
    @GetMapping("ip")
    public ResponseDTO<List<ResponseLoginHistoryVO>> getLoginHistory() {
        List<ResponseLoginHistoryVO> response = adminQueryService.getLoginHistory();
        return ResponseDTO.ok(response);
    }
}
