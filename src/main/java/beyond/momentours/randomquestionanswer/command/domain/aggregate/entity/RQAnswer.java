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

    @Column(name = "ques_id")
    private Long quesId;

    @Column(name = "member_id")
    private Long memberId;

}
