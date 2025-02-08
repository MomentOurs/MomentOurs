package beyond.momentours.randomquestionanswer.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_ques_answer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class RQAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ques_ans_id")
    private Long quesAnswerId;

    @Column(name = "ques_ans_content")
    private String quesAnsContent;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "user_ques_id")
    private Long userQuesId;

    @Column(name = "member_id")
    private Long memberId;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }

    // Update 되기 전에 실행
    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
