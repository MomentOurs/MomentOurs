package beyond.momentours.inquiry.command.domain.aggregate.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class InquiryAnswerCreateOrUpdateDTO {

    @JsonProperty("inquiry_answer_content")
    private String inquiryAnswerContent;


}
