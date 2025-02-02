package beyond.momentours.inquiry.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "tb_inquiry_answer")
public class InquiryAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_answer_id")
    private Long inquiryAnswerId;

    @Column(name = "inquiry_answer_content", nullable = false)
    private String inquiryAnswerContent;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime answerCreatedAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime answerUpdatedAt;

    @Column(name = "member_id", nullable = false)
    private Long answerMemberId;


    @Column(name = "inquiry_id", nullable = false )
    private Long inquiryId;

//    @OneToOne(fetch = FetchType.LAZY) // 관계의 주인으로 설정
//    @JoinColumn(name = "inquiry_id") // 외래 키로 사용되는 필드
//    private Inquiry inquiry; // 해당 답변은 어떤 문의에 대한 답변인지 명시


}
