package beyond.momentours.randomquestionanswer.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RQAnswerDTO {

    private Long quesAnswerId;
    private String quesAnsContent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long quesId;
    private Long memberId;
}
