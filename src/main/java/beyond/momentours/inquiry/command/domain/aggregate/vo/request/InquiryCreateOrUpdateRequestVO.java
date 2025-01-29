package beyond.momentours.inquiry.command.domain.aggregate.vo.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class InquiryCreateOrUpdateRequestVO {

    @JsonProperty("inquiry_title")
    private String inquiryTitle;

    @JsonProperty("inquiry_content")
    private String inquiryContent;
}
