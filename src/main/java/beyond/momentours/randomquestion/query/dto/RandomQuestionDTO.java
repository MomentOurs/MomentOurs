package beyond.momentours.randomquestion.query.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RandomQuestionDTO {
    private Long quesId;
    private String quesContent;
}
