package beyond.momentours.member.domain.repository;

import beyond.momentours.member.domain.aggregate.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByMemberEmail(String memberEmail);
}
