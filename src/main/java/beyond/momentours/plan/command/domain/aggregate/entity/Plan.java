package beyond.momentours.plan.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_plan")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "plan_title")
    private String planTitle;

    @Column(name = "plan_content")
    private String planContent;

    @Column(name = "plan_start_date")
    private LocalDateTime planStartDate;

    @Column(name = "plan_end_date")
    private LocalDateTime planEndDate;

    @Column(name = "plan_status")
    private Boolean planStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "couple_id")
    private Long coupleId;

    @Column(name = "course_id")
    private Long courseId;

    public void register(Plan plan) {
        plan.createdAt = LocalDateTime.now();
        plan.updatedAt = LocalDateTime.now();
    }

    public void setCourseId(Plan plan, Long courseId) {
        plan.courseId = courseId;
    }
}
