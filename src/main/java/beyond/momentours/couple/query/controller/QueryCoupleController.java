package beyond.momentours.couple.query.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.couple.command.application.dto.CoupleListDTO;
import beyond.momentours.couple.command.application.mapper.CoupleConverter;
import beyond.momentours.couple.command.domain.vo.response.CoupleListResponseVO;
import beyond.momentours.couple.query.service.QueryCoupleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/couple")
public class QueryCoupleController {
    private final QueryCoupleService coupleService;
    private final CoupleConverter converter;

    @Autowired
    public QueryCoupleController (QueryCoupleService coupleService,
                                  CoupleConverter coupleConverter) {
        this.coupleService = coupleService;
        this.converter = coupleConverter;
    }

    @GetMapping("/{coupleId}")
    public ResponseDTO<?> getCoupleById(@PathVariable("coupleId") Long coupleId) {
        CoupleListDTO coupleListDTO = coupleService.getCoupleById(coupleId);
        CoupleListResponseVO responseVO = converter.fromDtoToCoupleVO(coupleListDTO);
        return ResponseDTO.ok(responseVO);
    }
}
