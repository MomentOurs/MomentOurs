package beyond.momentours.inquiry.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "tb_inquiry")
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private Long inquiryId;

    @Column(name = "inquiry_title", nullable = false)
    private String inquiryTitle;

    @Column(name = "inquiry_content" , nullable = false)
    private String inquiryContent;

    @Column(name = "inquiry_answer_status", nullable = false)
    private Boolean inquiryAnswerStatus;

    @Column(name = "inquiry_status", nullable = false)
    private Boolean inquiryStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime inquiryCreatedAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime inquiryUpdatedAt;

    @Column(name = "member_id", nullable = false)
    private Long inquiryMemberId;

}
