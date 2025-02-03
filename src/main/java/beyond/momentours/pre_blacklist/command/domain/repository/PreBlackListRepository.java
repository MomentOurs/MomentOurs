package beyond.momentours.pre_blacklist.command.domain.repository;

import beyond.momentours.pre_blacklist.command.domain.aggregate.entity.PreBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreBlackListRepository extends JpaRepository<PreBlackList, Long> {
    PreBlackList findByMemberId(Long memberId);
}
