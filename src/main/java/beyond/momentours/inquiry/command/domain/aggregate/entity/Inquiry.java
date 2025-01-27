package beyond.momentours.inquiry.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "tb_inquiry")
@Entity
@Builder
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private Long inquiryId;

    @Column(name = "inquiry_title")
    private String inquiryTitle;

    @Column(name = "inquiry_content")
    private String inquiryContent;

    @Column(name = "inquiry_answer_status")
    private Boolean inquiryAnswerStatus;

    @Column(name = "inquiry_status")
    private Boolean inquiryStatus;

    @Column(name = "created_at")
    private LocalDateTime inquiryCreatedAt;

    @Column(name = "updated_at")
    private LocalDateTime inquiryUpdatedAt;

    @Column(name = "member_id")
    private Long inquiryMemberId;

    // OneToOne 관계 설정
    @OneToOne(mappedBy = "inquiry", fetch = FetchType.LAZY) // InquiryAnswer에서 이걸 참조하는 필드명
    private InquiryAnswer inquiryAnswer;
}
