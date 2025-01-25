package beyond.momentours.blacklist.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "tb_blacklist")
public class BlackList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "black_status", nullable = false)
    private Boolean blackStatus = true;

    @Column(name = "black_date", nullable = false)
    private LocalDateTime blackDate = LocalDateTime.now();;

    @Column(name = "accessible_date")
    private LocalDateTime accessibleDate;

}
