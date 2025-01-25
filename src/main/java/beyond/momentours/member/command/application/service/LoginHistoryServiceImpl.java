package beyond.momentours.member.command.application.service;

import beyond.momentours.member.command.domain.aggregate.entity.LoginHistory;
import beyond.momentours.member.command.domain.repository.LoginHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginHistoryServiceImpl implements LoginHistoryService {

    private final LoginHistoryRepository loginHistoryRepository;

    @Autowired
    public LoginHistoryServiceImpl(LoginHistoryRepository loginHistoryRepository) {
        this.loginHistoryRepository = loginHistoryRepository;
    }

    @Transactional
    @Override
    public void recordLogin(Long memberId, String ipAddress) {
        LoginHistory loginHistory = LoginHistory.builder()
                .memberId(memberId)
                .loginHistoryIp(ipAddress)
                .build();

        loginHistoryRepository.save(loginHistory);
    }
}
