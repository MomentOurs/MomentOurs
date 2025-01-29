package beyond.momentours.inquiry.command.application.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class InquiryAnswerDTO {

    private Long inquiryAnswerId;
    private String inquiryAnswerContent;
    private LocalDateTime answerCreatedAt;
    private LocalDateTime answerUpdatedAt;
    private Long answerMemberId;
    private Long inquirerId;



}
