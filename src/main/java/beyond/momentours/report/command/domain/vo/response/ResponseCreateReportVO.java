package beyond.momentours.report.command.domain.vo.response;

import beyond.momentours.report.command.domain.aggregate.ProcessStatus;
import beyond.momentours.report.command.domain.aggregate.ReportReason;
import beyond.momentours.report.command.domain.aggregate.ReportType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseCreateReportVO {
    private Long reportId;
    private ReportType reportType;
    private ReportReason reportReason;
    private ProcessStatus processStatus;
    private Long targetId;
    private LocalDateTime createdAt;
    private Long reportedUserId;
    private Long reportUserId;
}
