package beyond.momentours.couple.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "tb_couple_list")
public class CoupleList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couple_id")
    private Long coupleId;

    @Column(name = "couple_name")
    private String coupleName;

    @Column(name = "couple_photo")
    private String couplePhoto;

    @Column(name = "couple_start_date")
    private LocalDateTime coupleStartDate;

    @Column(name = "couple_status")
    private Boolean coupleStatus;

    @Column(name = "couple_matching_status")
    private CoupleMatchingStatus coupleMatchingStatus;

    @Column(name = "member_id1")
    private Long memberId1;

    @Column(name = "member_id2")
    private Long memberId2;
}
