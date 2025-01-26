package beyond.momentours.randomquestion.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RandomQuestionDTO {
    private Long quesId;
    private String quesContent;
    private String ansStatus;
    private LocalDateTime createdAt;
    private Long coupleId;
}
