package beyond.momentours.randomquestionanswer.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class RQAnswerDTO {

    private Long quesAnswerId;
    private String quesAnsContent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userQuesId;
    private Long memberId;
}
