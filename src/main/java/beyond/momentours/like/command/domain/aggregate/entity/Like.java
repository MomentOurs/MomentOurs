package beyond.momentours.like.command.domain.aggregate.entity;

import beyond.momentours.like.command.domain.aggregate.LikeType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_like")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "like_type", nullable = false)
    private LikeType likeType;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
