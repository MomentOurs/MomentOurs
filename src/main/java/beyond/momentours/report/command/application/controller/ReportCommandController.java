package beyond.momentours.report.command.application.controller;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.report.command.application.dto.ReportDTO;
import beyond.momentours.report.command.application.mapper.ReportConverter;
import beyond.momentours.report.command.application.service.ReportCommandService;
import beyond.momentours.report.command.domain.vo.request.RequestCreateReportVO;
import beyond.momentours.report.command.domain.vo.response.ResponseCreateReportVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/report")
@Slf4j
@RequiredArgsConstructor
public class ReportCommandController {

    private final ReportCommandService reportCommandService;
    private final ReportConverter reportConverter;

    @PostMapping
    public ResponseEntity<?> createReport(@RequestBody RequestCreateReportVO request, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("신고 요청 데이터: {}", request);
        try {
            ReportDTO reportDTO = reportConverter.fromCreateVOToDTO(request, user.getMemberId());
            ReportDTO savedReport = reportCommandService.createReport(reportDTO);
            ResponseCreateReportVO response = reportConverter.fromDTOToCreateVO(savedReport);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (CommonException e) {
            log.error("신고 생성 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }
}
