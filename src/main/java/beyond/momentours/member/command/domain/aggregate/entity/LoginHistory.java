package beyond.momentours.member.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "tb_login_history")
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_history_id", nullable = false)
    private Long loginHistoryId;

    @Column(name = "login_history_date", nullable = false)
    private LocalDateTime loginHistoryDate;

    @Column(name = "login_history_ip", nullable = false)
    private String loginHistoryIp;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @PrePersist
    private void prePersist() {
        this.loginHistoryDate = LocalDateTime.now();
    }

}
