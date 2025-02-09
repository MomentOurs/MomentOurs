package beyond.momentours.randomquestion.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "tb_random_question")
public class RandomQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ques_id", nullable = false)
    private Long quesId;

    @Column(name = "ques_content", nullable = false)
    private String quesContent;

}
