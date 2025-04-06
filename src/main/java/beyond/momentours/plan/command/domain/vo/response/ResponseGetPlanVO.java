package beyond.momentours.plan.command.domain.vo.response;

import beyond.momentours.plan.command.domain.aggregate.enums.PlanType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResponseGetPlanVO {
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
    private Long courseId;
}
