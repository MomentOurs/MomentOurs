package beyond.momentours.date_course.command.domain.aggregate.entity;

import beyond.momentours.date_course.command.domain.aggregate.CourseType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_date_course")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DateCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "course_title", nullable = false)
    private String courseTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_type", nullable = false)
    private CourseType courseType;

    @Column(name = "course_memo")
    private String courseMemo;

    @Column(name = "course_disclosure", nullable = false)
    private Boolean courseDisclosure;

    @Column(name = "course_like")
    private Long courseLike;

    @Column(name = "course_view")
    private Long courseView;

    @Column(name = "course_status", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean courseStatus = true;

    @Column(name = "course_start_date", nullable = false)
    private LocalDateTime courseStartDate;

    @Column(name = "course_end_date", nullable = false)
    private LocalDateTime courseEndDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @PrePersist
    private void onCreate() {
        this.courseLike = 0L;
        this.courseView = 0L;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateCourseTitle(String courseTitle) {
        if (courseTitle != null) {
            this.courseTitle = courseTitle;
        }
    }

    public void updateCourseType(CourseType courseType) {
        if (courseType != null) {
            this.courseType = courseType;
        }
    }

    public void updateCourseMemo(String courseMemo) {
        if (courseMemo != null) {
            this.courseMemo = courseMemo;
        }
    }

    public void updateCourseDisclosure(Boolean courseDisclosure) {
        if (courseDisclosure != null) {
            this.courseDisclosure = courseDisclosure;
        }
    }

    public void updateCourseStartDate(LocalDateTime courseStartDate) {
        if (courseStartDate != null) {
            this.courseStartDate = courseStartDate;
        }
    }

    public void updateCourseEndDate(LocalDateTime courseEndDate) {
        if (courseEndDate != null) {
            this.courseEndDate = courseEndDate;
        }
    }

    public void updateUpdatedAt(LocalDateTime updatedAt) {
        if (updatedAt != null) {
            this.updatedAt = updatedAt;
        }
    }

    public void deleteCourse(Boolean courseStatus) {
        this.courseStatus = courseStatus;
    }
}
