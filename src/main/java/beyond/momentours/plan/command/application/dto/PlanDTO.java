package beyond.momentours.plan.command.application.dto;

import beyond.momentours.plan.command.domain.aggregate.enums.PlanType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PlanDTO {
    private Long planId;
    private PlanType planType;
    private String planTitle;
    private String planContent;
    private LocalDateTime planStartDate;
    private LocalDateTime planEndDate;
    private LocalDateTime planReminderDatetime;
    private Boolean planStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long memberId;
    private Long coupleId;
    private Long courseId;
}
