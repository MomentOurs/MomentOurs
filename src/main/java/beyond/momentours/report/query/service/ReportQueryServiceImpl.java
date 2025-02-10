package beyond.momentours.report.query.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.report.command.application.dto.ReportDTO;
import beyond.momentours.report.command.domain.aggregate.entity.Report;
import beyond.momentours.report.command.domain.repository.ReportRepository;
import beyond.momentours.report.query.repository.ReportMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportQueryServiceImpl implements ReportQueryService {

    private final ReportMapper reportMapper;
    private final ReportRepository reportRepository;

    @Override
    public ReportDTO getReportById(Long reportId) {
        try {
            ReportDTO report = reportMapper.findReportById(reportId);
            if (report == null) throw new CommonException(ErrorCode.NOT_FOUND_REPORT);
            return report;
        } catch (Exception e) {
            log.error("신고 상세 조회 오류", e);
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public void updateReportsWithPreBlackId(Long reportedUserId, Long preBlackId) {
        try {
            List<Report> reports = reportMapper.findRecentReportsByReportedUserId(reportedUserId);
            for (Report report : reports) report.setPreBlackId(preBlackId);

            reportRepository.saveAll(reports);

        } catch (Exception e) {
            throw new CommonException(ErrorCode.REPORT_UPDATE_FAILURE);
        }
    }
}
