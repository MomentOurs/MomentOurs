package beyond.momentours.couple.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.couple.command.application.dto.CoupleListDTO;
import beyond.momentours.couple.command.application.dto.CoupleProfileDTO;
import beyond.momentours.couple.command.application.mapper.CoupleConverter;
import beyond.momentours.couple.command.domain.aggregate.entity.CoupleList;
import beyond.momentours.couple.command.domain.repository.CoupleRepository;
import beyond.momentours.couple.command.domain.vo.request.CoupleProfileRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class CommandCoupleServiceImpl implements CommandCoupleService {
    private final CoupleRepository coupleRepository;
    private final CoupleConverter coupleConverter;

    @Autowired
    public CommandCoupleServiceImpl(CoupleRepository coupleRepository,
                                    CoupleConverter coupleConverter) {
        this.coupleRepository = coupleRepository;
        this.coupleConverter = coupleConverter;
    }

    @Override
    public CoupleProfileDTO updateProfile(CoupleProfileRequestVO requestVO) {
        log.info("couple profile 수정 메서드 시작, requestVO: {}", requestVO);
        CoupleList existingCouple = coupleRepository.findCoupleListByCoupleId(requestVO.getCoupleId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_COUPLE));
        existingCouple.update(requestVO);
        coupleRepository.save(existingCouple);
        log.info("수정된 커플 객체 정보: {}", existingCouple);
        return coupleConverter.fromEntityToProfileDTO(existingCouple);
    }

    @Override
    public CoupleListDTO deleteCouple(Long coupleId) {
        log.info("deleteCouple 메서드 시작, coupleId: {}", coupleId);
        CoupleList existingCouple = coupleRepository.findCoupleListByCoupleId(coupleId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_COUPLE));
        existingCouple.delete();
        coupleRepository.save(existingCouple);
        log.info("삭제된 커플 객체 정보: {}", existingCouple);
        return coupleConverter.fromEntityToCoupleDTO(existingCouple);
    }
}
