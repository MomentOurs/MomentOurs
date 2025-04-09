package beyond.momentours.inquiry.command.application.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class InquiryDTO {

    private Long inquiryId;
    private String inquiryTitle;
    private String inquiryContent;
    private Boolean inquiryAnswerStatus;
    private Boolean inquiryStatus;
    private LocalDateTime inquiryCreatedAt;
    private LocalDateTime inquiryUpdatedAt;
    private Long inquiryMemberId;
}
