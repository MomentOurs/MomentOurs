package beyond.momentours.member.domain.aggregate.entity;

import beyond.momentours.plan.command.domain.aggregate.entity.Plan;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "tb_member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "member_email", nullable = false)
    private String memberEmail;

    @Column(name = "member_password", nullable = false)
    private String memberPassword;

    @Column(name = "member_name", nullable = false)
    private String memberName;

    @Column(name = "member_nickname", nullable = false)
    private String memberNickname;

    @Column(name = "member_phone", nullable = false)
    private String memberPhone;

    @Column(name = "member_birth", nullable = false)
    private String memberBirth;

    @Column(name = "member_gender", nullable = false)
    private String memberGender;

    @Column(name = "member_mbti")
    private String memberMbti;

    @Column(name = "member_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole = MemberRole.ROLE_MEMBER;

    @Column(name = "member_status", nullable = false)
    private Boolean memberStatus = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void memberInfo(String memberEmail, String memberRole) {
        this.memberEmail = memberEmail;
        this.memberRole = MemberRole.valueOf(memberRole);
    }

}
