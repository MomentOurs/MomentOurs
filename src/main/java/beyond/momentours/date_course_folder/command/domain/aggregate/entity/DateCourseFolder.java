package beyond.momentours.date_course_folder.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_date_course_folder")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DateCourseFolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_id")
    private Long folderId;

    @Column(name = "folder_name", nullable = false)
    private String folderName;

    @Column(name = "member_id", nullable = false)
    private Long memberId;
}
