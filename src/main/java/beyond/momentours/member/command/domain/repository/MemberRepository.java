package beyond.momentours.member.command.domain.repository;

import beyond.momentours.member.command.domain.aggregate.entity.Member;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByMemberEmail(String memberEmail);

    @Modifying
    @Query("UPDATE Member m SET m.memberPassword = :memberPassword WHERE m.memberEmail = :memberEmail")
    void updatePasswordByEmail(@Param("memberEmail")String memberEmail, @Param("memberPassword")String memberPassword);
}
