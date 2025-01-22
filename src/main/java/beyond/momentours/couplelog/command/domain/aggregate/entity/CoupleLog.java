package beyond.momentours.couplelog.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_couple_log")
@Builder
public class CoupleLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couple_log_id")
    private Long coupleLogId;

    @Column(name = "couple_log_content")
    private String coupleLogContent;

    @Column(name = "couple_log_status")
    private Boolean coupleLogStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "couple_id")
    private Long coupleId;

    @Column(name = "member_id")
    private Long memberId; // 마지막 수정자 Pk

    public void create(String textContent, Long coupleId, Long memberId) {
        this.coupleLogContent = textContent;
        this.coupleId = coupleId;
        this.memberId = memberId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.coupleLogStatus = true;
    }

    public void updateContent (String content, Long memberId) {
        this.coupleLogContent = content;
        this.memberId = memberId;
        this.updatedAt = LocalDateTime.now();
    }
}
