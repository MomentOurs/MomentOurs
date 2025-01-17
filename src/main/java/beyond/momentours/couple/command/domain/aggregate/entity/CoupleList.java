package beyond.momentours.couple.command.domain.aggregate.entity;

import beyond.momentours.couple.command.domain.vo.CoupleProfileRequestVO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
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
    @Enumerated(EnumType.STRING)
    private CoupleMatchingStatus coupleMatchingStatus;

    @Column(name = "member_id1")
    private Long memberId1;

    @Column(name = "member_id2")
    private Long memberId2;

    public void create(Long memberId1, Long memberId2, String memberName1, String memberName2) {
        this.memberId1 = memberId1;
        this.memberId2 = memberId2;
        this.coupleMatchingStatus = CoupleMatchingStatus.AUTHENTICATED;
        this.coupleStatus = Boolean.FALSE;
        this.coupleStartDate = LocalDateTime.now();
        this.coupleName = memberName1 +" " +memberName2;
    }

    public void update(CoupleProfileRequestVO requestVO) {
        if (requestVO.getCoupleName() != null) {
            this.coupleName = requestVO.getCoupleName();
        }
        if (requestVO.getCouplePhoto() != null) {
            this.couplePhoto = requestVO.getCouplePhoto();
        }
        if (requestVO.getCoupleStartDate() != null) {
            this.coupleStartDate = requestVO.getCoupleStartDate();
        }
    }
}
