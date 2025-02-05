package beyond.momentours.report.command.application.service;

import beyond.momentours.report.command.application.dto.ReportDTO;

public interface ReportService {
    ReportDTO createReport(ReportDTO reportDTO);
}
