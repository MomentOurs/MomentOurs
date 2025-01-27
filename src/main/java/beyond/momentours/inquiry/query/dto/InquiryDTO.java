package beyond.momentours.inquiry.query.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class InquiryDTO {

    @JsonProperty("inquiry_id")
    private Long inquiryId;

    @JsonProperty("inquiry_title")
    private String inquiryTitle;

    @JsonProperty("inquiry_content")
    private String inquiryContent;

    @JsonProperty("inquiry_answer_status")
    private Boolean inquiryAnswerStatus;

    @JsonProperty("inquiry_status")
    private Boolean inquiryStatus;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

}
