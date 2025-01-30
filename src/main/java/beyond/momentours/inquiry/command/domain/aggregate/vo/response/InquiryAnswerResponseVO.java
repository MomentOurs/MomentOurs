package beyond.momentours.inquiry.command.domain.aggregate.vo.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class InquiryAnswerResponseVO {

    @JsonProperty("inquiry_answer_id")
    private Long inquiryAnswerId;

    @JsonProperty("inquiry_answer_content")
    private String inquiryAnswerContent;

    @JsonProperty("created_at")
    private LocalDateTime answerCreatedAt;

    @JsonProperty("updated_at")
    private LocalDateTime answerUpdatedAt;

    @JsonProperty("member_id")
    private Long answerMemberId;

    @JsonProperty("inquiry_id")
    private Long inquiryId;


}
