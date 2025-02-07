package beyond.momentours.report.query.controller;

import beyond.momentours.report.command.application.dto.ReportDTO;
import beyond.momentours.report.query.service.ReportQueryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/report")
@Slf4j
@RequiredArgsConstructor
public class ReportQueryController {

    private final ReportQueryService reportService;

    @Operation(description = "예비 블랙리스트로 등록된 신고 리스트 중 하나의 신고 상세 조회")
    @GetMapping("/{reportId}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable Long reportId) {
        ReportDTO report = reportService.getReportById(reportId);
        return ResponseEntity.ok(report);
    }
}
