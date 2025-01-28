package beyond.momentours.inquiry.command.domain.aggregate.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InquiryAnswerCreateOrUpdateDTO {

    @JsonProperty("inquiry_answer_content")
    private String inquiryAnswerContent;


}
