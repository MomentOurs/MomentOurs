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

    @Column(name = "course_certification", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean courseCertification = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "folder_id", nullable = false)
    private Long folderId;

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

    public void certifyCourse() {
        this.courseCertification = true;
    }

    public void updateSchedule(LocalDateTime courseStartDate, LocalDateTime courseEndDate) {
        if (courseStartDate == null || courseEndDate == null) {
            throw new IllegalArgumentException("시작 날짜와 종료 날짜를 모두 입력해야 합니다.");
        }
        if (courseEndDate.isBefore(courseStartDate)) {
            throw new IllegalArgumentException("종료 날짜는 시작 날짜보다 이후여야 합니다.");
        }

        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.updatedAt = LocalDateTime.now();
    }
}
