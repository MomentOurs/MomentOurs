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
                .reportedUserId(request.getReportedUserId())
                .reportUserId(memberId)
                .momentId(request.getMomentId())
                .courseId(request.getCourseId())
                .build();
    }

    public ResponseCreateReportVO fromDTOToCreateVO(Report report) {
        return ResponseCreateReportVO.builder()
                .reportId(report.getReportId())
                .reportType(report.getReportType())
                .reportReason(report.getReportReason())
                .processStatus(report.getProcessStatus())
                .createdAt(report.getCreatedAt())
                .reportedUserId(report.getReportedUserId())
                .reportUserId(report.getReportUserId())
                .momentId(report.getMomentId())
                .courseId(report.getCourseId())
                .build();
    }

    public Report fromDTOToEntity(ReportDTO reportDTO) {
        return Report.builder()
                .reportId(reportDTO.getReportId())
                .reportType(reportDTO.getReportType())
                .reportReason(reportDTO.getReportReason())
                .processStatus(reportDTO.getProcessStatus())
                .reportedUserId(reportDTO.getReportedUserId())
                .reportUserId(reportDTO.getReportUserId())
                .momentId(reportDTO.getMomentId())
                .courseId(reportDTO.getCourseId())
                .build();
    }

    public ReportDTO fromEntityToDTO(Report savedReport) {
        return ReportDTO.builder()
                .reportId(savedReport.getReportId())
                .reportType(savedReport.getReportType())
                .reportReason(savedReport.getReportReason())
                .processStatus(savedReport.getProcessStatus())
                .reportedUserId(savedReport.getReportedUserId())
                .reportUserId(savedReport.getReportUserId())
                .momentId(savedReport.getMomentId())
                .courseId(savedReport.getCourseId())
                .build();
    }

    public ResponseCreateReportVO fromDTOToCreateVO(ReportDTO savedReport) {
        return ResponseCreateReportVO.builder()
                .reportId(savedReport.getReportId())
                .reportType(savedReport.getReportType())
                .reportReason(savedReport.getReportReason())
                .processStatus(savedReport.getProcessStatus())
                .reportedUserId(savedReport.getReportedUserId())
                .reportUserId(savedReport.getReportUserId())
                .momentId(savedReport.getMomentId())
                .courseId(savedReport.getCourseId())
                .build();
    }
}
