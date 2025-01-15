package beyond.momentours.plan.command.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestRegisterPlanVO {
    @JsonProperty("plan_title")
    private String planTitle;

    @JsonProperty("plan_content")
    private String planContent;

    @JsonProperty("plan_start_date")
    private LocalDateTime planStartDate;

    @JsonProperty("plan_end_date")
    private LocalDateTime planEndDate;

    @JsonProperty("plan_reminder_datetime")
    private LocalDateTime planReminderDatetime;

    @JsonProperty("course_id")
    private Long courseId;
}
