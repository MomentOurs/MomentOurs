package beyond.momentours.inquiry.query.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class InquiryAnswerDTO {

    @JsonProperty("inquiry_answer_id")
    private Long inquiryAnswerId;

    @JsonProperty("inquiry_answer_content")
    private String inquiryAnswerContent;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("member_id")
    private Long memberId;

    @JsonProperty("inquiry_id")
    private Long inquiryId;

}
