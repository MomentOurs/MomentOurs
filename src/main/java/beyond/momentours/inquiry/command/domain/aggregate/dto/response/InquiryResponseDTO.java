package beyond.momentours.inquiry.command.domain.aggregate.dto.response;


import beyond.momentours.inquiry.command.domain.aggregate.entity.InquiryAnswer;
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
public class InquiryResponseDTO {

    @JsonProperty("inquiry_id")
    private String inquiryId;

    @JsonProperty("inquiry_title")
    private String inquiryTitle;

    @JsonProperty("inquiry_content")
    private String inquiryContent;

    @JsonProperty("inquiry_answer_status")
    private Boolean inquiryAnswerStatus;

    @JsonProperty("inquiry_status")
    private Boolean inquiryStatus;

    @JsonProperty("created_at")
    private LocalDateTime inquiryCreatedAt;

    @JsonProperty("updated_at")
    private LocalDateTime inquiryUpdatedAt;

    @JsonProperty("member_id")
    private Long inquiryMemberId;

    @JsonProperty("inquiry_id")
    private InquiryAnswer inquiryAnswer;






}
