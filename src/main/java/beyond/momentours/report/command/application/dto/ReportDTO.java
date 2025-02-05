package beyond.momentours.report.command.application.dto;

import beyond.momentours.report.command.domain.aggregate.ProcessStatus;
import beyond.momentours.report.command.domain.aggregate.ReportReason;
import beyond.momentours.report.command.domain.aggregate.ReportType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReportDTO {
    private Long reportId;
    private ReportType reportType;
    private ReportReason reportReason;
    private ProcessStatus processStatus;
    private LocalDateTime createdAt;
    private Long reportedUserId;
    private Long reportUserId;
    private Long momentId;
    private Long courseId;
}
