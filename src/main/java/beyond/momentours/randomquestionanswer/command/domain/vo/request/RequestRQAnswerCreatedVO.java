package beyond.momentours.randomquestionanswer.command.domain.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestRQAnswerCreatedVO {
    private String quesAnsContent;
    private Long userQuesId;
}
