package beyond.momentours.inquiry.command.domain.aggregate.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class InquiryAnswerResponseDTO {

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
