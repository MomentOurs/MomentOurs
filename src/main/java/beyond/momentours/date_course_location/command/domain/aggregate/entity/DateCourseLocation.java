package beyond.momentours.date_course_location.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_date_course_location")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DateCourseLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_location_id")
    private Long courseLocationId;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(name = "location_id", nullable = false)
    private Long locationId;

    @Column(name = "course_location_status", nullable = false)
    private Boolean courseLocationStatus;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;
}
