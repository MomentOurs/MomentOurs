package beyond.momentours.plan.command.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseRegisterPlanVO {
    @JsonProperty("plan_title")
    private String planTitle;

    @JsonProperty("plan_content")
    private String planContent;

    @JsonProperty("plan_start_date")
    private LocalDateTime planStartDate;

    @JsonProperty("plan_end_date")
    private LocalDateTime planEndDate;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("member_id")
    private Long memberId;

    @JsonProperty("couple_id")
    private Long coupleId;

    @JsonProperty("course_id")
    private Long courseId;
}
