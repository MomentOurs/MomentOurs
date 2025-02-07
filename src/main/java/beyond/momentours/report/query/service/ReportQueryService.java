package beyond.momentours.report.query.service;

import beyond.momentours.report.command.application.dto.ReportDTO;
import jakarta.transaction.Transactional;

public interface ReportQueryService {
    ReportDTO getReportById(Long reportId);

    @Transactional
    void updateReportsWithPreBlackId(Long reportedUserId, Long preBlackId);
}
