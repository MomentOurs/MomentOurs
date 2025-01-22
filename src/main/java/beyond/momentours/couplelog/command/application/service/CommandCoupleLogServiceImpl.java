package beyond.momentours.couplelog.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.couple.command.application.dto.CoupleListDTO;
import beyond.momentours.couple.query.service.QueryCoupleService;
import beyond.momentours.couplelog.command.application.dto.CoupleLogDTO;
import beyond.momentours.couplelog.command.domain.aggregate.entity.CoupleLog;
import beyond.momentours.couplelog.command.domain.repository.CoupleLogRepository;
import beyond.momentours.couplelog.command.domain.vo.request.CoupleLogRequestVO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class CommandCoupleLogServiceImpl implements CommandCoupleLogService {
    private final CoupleLogRepository coupleLogRepository;
    private final QueryCoupleService coupleService;

    @Autowired
    public CommandCoupleLogServiceImpl(CoupleLogRepository coupleLogRepository,
                                       QueryCoupleService coupleService) {
        this.coupleLogRepository = coupleLogRepository;
        this.coupleService = coupleService;
    }

    @Override
    public void createNewCoupleLog(CoupleLogRequestVO requestVO, CustomUserDetails user) {
        log.info("새로운 커플로그를 작성하는 Service method 시작(createNewCoupleLog)");
        // memberId가 커플 번호와 일치하는지 확인
        Long memberId = user.getMember().getMemberId();
        Long coupleId = requestVO.getCoupleId();

        CoupleListDTO existingCouple = coupleService.getCoupleByMemberId(memberId);
        if (existingCouple == null) {
            log.info("커플이 존재하지 않는 회원 번호로 커플을 찾는 경우 발생하는 에러(memberId): {}", memberId);
            throw new CommonException(ErrorCode.NOT_FOUND_COUPLE);
        }
        if (existingCouple.getCoupleId() != coupleId) {
            log.error("requestVO에 등록된 coupleId가 조회된 커플 Id와 일치하지 않을 때 발생하는 에러(coupleId): {}", coupleId);
            throw new CommonException(ErrorCode.FORBIDDEN_ROLE);
        }

        CoupleLog newCoupleLog = new CoupleLog();
        newCoupleLog.create(requestVO.getTextContent(), coupleId, memberId);

        log.info("새로 생긴 커플로그 엔티티 정보: {}", newCoupleLog);

        coupleLogRepository.save(newCoupleLog);
    }

    @Override
    public CoupleLogDTO updateCoupleLog(Long couplelogId, Long memberId, CoupleLogRequestVO requestVO) {
        return null;
    }
}
