package beyond.momentours.randomquestionanswer.query.vo.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRQAnswerVO {
    private String myAnswer;
    private String otherAnswer;  // 상대방의 답변
    private String message;
    private Long myQuesAnsId;
}
