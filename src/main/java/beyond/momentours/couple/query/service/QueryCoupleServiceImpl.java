package beyond.momentours.couple.query.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.couple.command.application.dto.CoupleListDTO;
import beyond.momentours.couple.command.application.mapper.CoupleConverter;
import beyond.momentours.couple.command.domain.aggregate.entity.CoupleList;
import beyond.momentours.couple.query.repository.CoupleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class QueryCoupleServiceImpl implements QueryCoupleService {
    private final CoupleMapper coupleMapper;
    private final CoupleConverter converter;

    @Autowired
    public QueryCoupleServiceImpl(CoupleMapper coupleMapper, CoupleConverter converter) {
        this.coupleMapper = coupleMapper;
        this.converter = converter;
    }

    @Override
    public CoupleListDTO getCoupleById(Long coupleId) {
        CoupleList existingCouple = coupleMapper.getCoupleByCoupleId(coupleId);
        if (existingCouple == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_COUPLE);
        }
        return converter.fromEntityToCoupleDTO(existingCouple);
    }
}
