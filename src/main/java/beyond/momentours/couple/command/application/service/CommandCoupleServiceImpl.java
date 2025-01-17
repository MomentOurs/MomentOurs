package beyond.momentours.couple.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.couple.command.application.dto.CoupleProfileDTO;
import beyond.momentours.couple.command.domain.aggregate.entity.CoupleList;
import beyond.momentours.couple.command.domain.repository.CoupleRepository;
import beyond.momentours.couple.command.domain.vo.CoupleProfileRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class CommandCoupleServiceImpl implements CommandCoupleService {
    private final CoupleRepository coupleRepository;

    @Autowired
    public CommandCoupleServiceImpl(CoupleRepository coupleRepository) {
        this.coupleRepository = coupleRepository;
    }

    @Override
    public CoupleProfileDTO editProfile(CoupleProfileRequestVO requestVO) {
        log.info("couple profile 수정 메서드 시작, requestVO: {}", requestVO);
        CoupleList existingCouple = coupleRepository.findCoupleListByCoupleId(requestVO.getCoupleId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_COUPLE));
        existingCouple.update(requestVO);
        log.info("수정된 커플 객체 정보: {}", existingCouple);
        return CoupleProfileDTO.builder()
                .coupleId(existingCouple.getCoupleId())
                .coupleName(existingCouple.getCoupleName())
                .couplePhoto(existingCouple.getCouplePhoto())
                .coupleStartDate(existingCouple.getCoupleStartDate())
                .build();
    }
}
