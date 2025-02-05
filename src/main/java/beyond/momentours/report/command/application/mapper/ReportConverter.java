package beyond.momentours.report.command.application.mapper;

import beyond.momentours.report.command.application.dto.ReportDTO;
import beyond.momentours.report.command.domain.aggregate.entity.Report;
import beyond.momentours.report.command.domain.vo.request.RequestCreateReportVO;
import beyond.momentours.report.command.domain.vo.response.ResponseCreateReportVO;
import org.springframework.stereotype.Component;

@Component
public class ReportConverter {

    public ReportDTO fromCreateVOToDTO(RequestCreateReportVO request, Long memberId) {
        return ReportDTO.builder()
                .reportType(request.getReportType())
                .reportReason(request.getReportReason())
                .processStatus(request.getProcessStatus())
                .targetId(request.getTargetId())
                .reportedUserId(request.getReportedUserId())
                .reportUserId(memberId)
                .build();
    }

    public ResponseCreateReportVO fromDTOToCreateVO(Report report) {
        return ResponseCreateReportVO.builder()
                .reportId(report.getReportId())
                .reportType(report.getReportType())
                .reportReason(report.getReportReason())
                .processStatus(report.getProcessStatus())
                .targetId(report.getTargetId())
                .createdAt(report.getCreatedAt())
                .reportedUserId(report.getReportedUserId())
                .reportUserId(report.getReportUserId())
                .build();
    }

    public Report fromDTOToEntity(ReportDTO reportDTO) {
        return Report.builder()
                .reportId(reportDTO.getReportId())
                .reportType(reportDTO.getReportType())
                .reportReason(reportDTO.getReportReason())
                .processStatus(reportDTO.getProcessStatus())
                .targetId(reportDTO.getTargetId())
                .reportedUserId(reportDTO.getReportedUserId())
                .reportUserId(reportDTO.getReportUserId())
                .build();
    }

    public ReportDTO fromEntityToDTO(Report savedReport) {
        return ReportDTO.builder()
                .reportId(savedReport.getReportId())
                .reportType(savedReport.getReportType())
                .reportReason(savedReport.getReportReason())
                .processStatus(savedReport.getProcessStatus())
                .targetId(savedReport.getTargetId())
                .reportedUserId(savedReport.getReportedUserId())
                .reportUserId(savedReport.getReportUserId())
                .build();
    }

    public ResponseCreateReportVO fromDTOToCreateVO(ReportDTO savedReport) {
        return ResponseCreateReportVO.builder()
                .reportId(savedReport.getReportId())
                .reportType(savedReport.getReportType())
                .reportReason(savedReport.getReportReason())
                .processStatus(savedReport.getProcessStatus())
                .targetId(savedReport.getTargetId())
                .reportedUserId(savedReport.getReportedUserId())
                .reportUserId(savedReport.getReportUserId())
                .build();
    }
}
