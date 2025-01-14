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

    @Column(name = "plan_title", nullable = false)
    private String planTitle;

    @Column(name = "plan_content", nullable = false)
    private String planContent;

    @Column(name = "plan_start_date", nullable = false)
    private LocalDateTime planStartDate;

    @Column(name = "plan_end_date", nullable = false)
    private LocalDateTime planEndDate;

    @Column(name = "plan_reminder_datetime")
    private LocalDateTime planReminderDatetime;

    @Column(name = "plan_status", nullable = false)
    private Boolean planStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "couple_id", nullable = false)
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

    public void updateTitle(String planTitle) {
        if (planTitle != null) {
            this.planTitle = planTitle;
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void updateContent(String planContent) {
        if (planContent != null) {
            this.planContent = planContent;
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void updateStartDate(LocalDateTime planStartDate) {
        if (planStartDate != null) {
            this.planStartDate = planStartDate;
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void updateEndDate(LocalDateTime planEndDate) {
        if (planEndDate != null) {
            this.planEndDate = planEndDate;
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void updateReminderDatetime(LocalDateTime planReminderDatetime) {
        if (planReminderDatetime != null) {
            this.planReminderDatetime = planReminderDatetime;
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void updateCourseId(Long courseId) {
        if (courseId != null) {
            this.courseId = courseId;
            this.updatedAt = LocalDateTime.now();
        }
    }

}
