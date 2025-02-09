package beyond.momentours.randomquestion.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserRandomQuestionDTO {

    private Long userQuesId;
    private String ansStatus;
    private LocalDateTime createdAt;
    private Long coupleId;
    private Long quesId;
}
