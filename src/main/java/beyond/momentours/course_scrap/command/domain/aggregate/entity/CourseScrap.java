package beyond.momentours.course_scrap.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_course_scrap")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CourseScrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_scrap_id")
    private Long courseScrapId;

    @Column(name = "course_scrap_folder_id", nullable = false)
    private Long courseScrapFolderId;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
