package beyond.momentours.pre_blacklist.command.application.service;

import beyond.momentours.pre_blacklist.command.application.dto.PreBlackListDTO;
import org.springframework.transaction.annotation.Transactional;

public interface PreBlackListService {
    void updateStatus(PreBlackListDTO preBlacklistDTO);

    @Transactional
    Long getOrCreatePreBlacklist(Long memberId);
}
