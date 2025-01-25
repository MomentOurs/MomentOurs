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
public class InquiryAndInquiryAnswerDTO {

    // inquiry 엔티티 필드들
    @JsonProperty("inquiry_id")
    private Long inquiryId;

    @JsonProperty("inquiry_title")
    private String inquiryTitle;

    @JsonProperty("inquiry_answer_status")
    private Boolean inquiryAnswerStatus;

    @JsonProperty("inquiry_status")
    private Boolean inquiryStatus;

    @JsonProperty("inquiry_created_at")
    private LocalDateTime createdAt;

    @JsonProperty("inquiry_updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("inquiry_member_id")
    private Long inquiryMemberId;

    // inquiryAnswer 엔티티 필드들
    @JsonProperty("inquiry_answer_id")
    private Long inquiryAnswerId;

    @JsonProperty("inquiry_answer_content")
    private String inquiryAnswerContent;

    @JsonProperty("answer_created_at")
    private LocalDateTime answerCreatedAt;

    @JsonProperty("answer_updated_at")
    private LocalDateTime answerUpdatedAt;

    @JsonProperty("answer_member_id")
    private Long answerMemberId;


}
