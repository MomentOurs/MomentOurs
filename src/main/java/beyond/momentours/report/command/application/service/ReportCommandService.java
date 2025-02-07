package beyond.momentours.report.command.application.service;

import beyond.momentours.report.command.application.dto.ReportDTO;

public interface ReportCommandService {
    ReportDTO createReport(ReportDTO reportDTO);
}
