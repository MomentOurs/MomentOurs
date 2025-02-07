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
public class InquiryListDTO {


    // inquiry 엔티티 필드들
    @JsonProperty("inquiry_id")
    private Long inquiryId;

    @JsonProperty("inquiry_title")
    private String inquiryTitle;

    @JsonProperty("inquiry_status")
    private String inquiryStatus;

    @JsonProperty("inquiry_answer_status")
    private Boolean inquiryAnswerStatus;

    @JsonProperty("inquiry_created_at")
    private LocalDateTime createdAt;

    @JsonProperty("inquiry_updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("inquiry_member_id")
    private Long inquiryMemberId;
}
