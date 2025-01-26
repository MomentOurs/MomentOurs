package beyond.momentours.member.command.application.service;

import org.springframework.transaction.annotation.Transactional;

public interface LoginHistoryService {
    @Transactional
    void recordLogin(Long memberId, String ipAddress);
}
