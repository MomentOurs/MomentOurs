package beyond.momentours.location.query.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.location.query.service.LocationQueryService;
import beyond.momentours.location.query.vo.ResponseLocationMapVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/location")
@RequiredArgsConstructor
public class LocationQueryController {

    private final LocationQueryService locationQueryService;

    @GetMapping("/map")
    public ResponseDTO<List<ResponseLocationMapVO>> getLocationsOnMap(@RequestParam BigDecimal latitudeMin, @RequestParam BigDecimal latitudeMax, @RequestParam BigDecimal longitudeMin, @RequestParam BigDecimal longitudeMax) {
        List<ResponseLocationMapVO> result = locationQueryService.getLocationsInBounds(latitudeMin, latitudeMax, longitudeMin, longitudeMax);
        return ResponseDTO.ok(result);
    }
}
