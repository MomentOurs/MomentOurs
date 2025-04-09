package beyond.momentours.inquiry.command.domain.aggregate.vo.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class InquiryAnswerCreateOrUpdateVO {

    @JsonProperty("inquiry_answer_content")
    private String inquiryAnswerContent;


}
