package beyond.momentours.report.command.domain.vo.request;

import beyond.momentours.report.command.domain.aggregate.ProcessStatus;
import beyond.momentours.report.command.domain.aggregate.ReportReason;
import beyond.momentours.report.command.domain.aggregate.ReportType;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestCreateReportVO {
    private ReportType reportType;
    private ReportReason reportReason;
    private ProcessStatus processStatus;
    private Long targetId;
    private Long reportedUserId;
    private Long reportUserId;
}
