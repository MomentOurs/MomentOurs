package beyond.momentours.inquiry.command.domain.aggregate.vo.response;


import beyond.momentours.inquiry.command.domain.aggregate.entity.InquiryAnswer;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class InquiryResponseVO {

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
