package beyond.momentours.couplelog.command.application.service;

import beyond.momentours.couplelog.command.domain.repository.CoupleLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class CommandCoupleLogServiceImpl implements CommandCoupleLogService {
    private final CoupleLogRepository coupleLogRepository;

    @Autowired
    public CommandCoupleLogServiceImpl(CoupleLogRepository coupleLogRepository) {
        this.coupleLogRepository = coupleLogRepository;
    }
}
