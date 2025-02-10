package beyond.momentours.pre_blacklist.query.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.pre_blacklist.query.service.PreBlackListQueryService;
import beyond.momentours.pre_blacklist.query.vo.response.ResponsePreBlackListAll;
import beyond.momentours.report.command.application.dto.ReportDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Operation(description = "회원 별로 중복 없이 목록 조회")
    @GetMapping("members")
    public ResponseDTO<List<ResponsePreBlackListAll>> getPreBlacklistAll() {
        List<ResponsePreBlackListAll> response = preBlackListQueryService.getPreBlacklistAll();
        return ResponseDTO.ok(response);
    }

    @Operation(description = "특정 예비 블랙리스트에 속한 신고 데이터 조회 API")
    @GetMapping("/{preBlackListId}/reports")
    public ResponseEntity<List<ReportDTO>> getReportsByPreBlackList(@PathVariable Long preBlackListId) {
        List<ReportDTO> reports = preBlackListQueryService.getReportsByPreBlackList(preBlackListId);
        return ResponseEntity.ok(reports);
    }
}
