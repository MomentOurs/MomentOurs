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
        CoupleList existingCouple = coupleRepository.findCoupleListByCoupleId(requestVO.getCoupleId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_COUPLE));
        if (requestVO.getCoupleName() != null) {
            existingCouple.setCoupleName(requestVO.getCoupleName());
        }
        if (requestVO.getCouplePhoto() != null) {
            existingCouple.setCouplePhoto(requestVO.getCouplePhoto());
        }
        if (requestVO.getCoupleStartDate() != null) {
            existingCouple.setCoupleStartDate(requestVO.getCoupleStartDate());
        }
        return CoupleProfileDTO.builder()
                .coupleId(existingCouple.getCoupleId())
                .coupleName(existingCouple.getCoupleName())
                .couplePhoto(existingCouple.getCouplePhoto())
                .coupleStartDate(existingCouple.getCoupleStartDate())
                .build();
    }
}
