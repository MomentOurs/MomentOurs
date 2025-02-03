package beyond.momentours.pre_blacklist.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "tb_pre_black_list")
public class PreBlackList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pre_black_id", nullable = false)
    private Long preBlackId;

    @Column(name = "black_list_count", nullable = false)
    private int blackListCount = 0;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "status_created_at", nullable = false)
    private LocalDateTime statusCreatedAt;

    @Column(name = "status_updated_at", nullable = false)
    private LocalDateTime statusUpdatedAt;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "report_id", nullable = false)
    private Long reportId;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}

