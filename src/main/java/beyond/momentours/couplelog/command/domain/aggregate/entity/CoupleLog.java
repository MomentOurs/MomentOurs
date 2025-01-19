package beyond.momentours.couplelog.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_couple_log")
@ToString
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
}
