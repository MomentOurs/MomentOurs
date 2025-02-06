package beyond.momentours.report.command.domain.aggregate.entity;

import beyond.momentours.report.command.domain.aggregate.ProcessStatus;
import beyond.momentours.report.command.domain.aggregate.ReportReason;
import beyond.momentours.report.command.domain.aggregate.ReportType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_report")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "report_type", nullable = false)
    private ReportType reportType;

    @Column(name = "report_reason", nullable = false)
    private ReportReason reportReason;

    @Column(name = "process_status", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private ProcessStatus processStatus;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "reported_user_id", nullable = false)
    private Long reportedUserId;

    @Column(name = "report_user_id", nullable = false)
    private Long reportUserId;

    @Column(name = "pre_black_id")
    private Long preBlackId;

    public void setReportedUserId(Long reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public void setPreBlackId(Long preBlackId) { this.preBlackId = preBlackId; }
}
