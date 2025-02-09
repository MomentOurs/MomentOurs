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
@Table(name = "tb_user_random_question")
public class UserRandomQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_ques_id", nullable = false)
    private Long userQuesId;

    @Column(name = "ans_status", nullable = false)
    private String ansStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "used")
    private Boolean used = false;

    @Column(name = "couple_id", nullable = false)
    private Long coupleId;

    @Column(name = "ques_id", nullable = false)
    private Long quesId;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
