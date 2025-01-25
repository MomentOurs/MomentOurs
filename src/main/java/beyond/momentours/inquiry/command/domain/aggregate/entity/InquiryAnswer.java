package beyond.momentours.inquiry.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "tb_inquiry_answer")
@Entity
@Builder
public class InquiryAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_answer_id")
    private Long inquiryAnswerId;

    @Column(name = "inquiry_answer_content")
    private String inquiryAnswerContent;

    @Column(name = "created_at")
    private LocalDateTime answerCreatedAt;

    @Column(name = "updated_at")
    private LocalDateTime swerUpdatedAt;

    @Column(name = "member_id")
    private Long answerMemberId;

    @OneToOne(fetch = FetchType.LAZY) // 관계의 주인으로 설정
    @JoinColumn(name = "inquiry_id") // 외래 키로 사용되는 필드
    private Inquiry inquiry; // 해당 답변은 어떤 문의에 대한 답변인지 명시


}
