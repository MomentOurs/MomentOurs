package beyond.momentours.moment.query.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.moment.query.dto.MomentDTO;
import beyond.momentours.moment.query.service.MomentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("queryMomentController")
@RequestMapping("api/moment")
@RequiredArgsConstructor
public class MomentController {

    private final MomentService momentService;

    // 내가 쓴 추억 목록 조회
    @GetMapping("/my-moments")
    public ResponseDTO<List<MomentDTO>> getMyMoments(@AuthenticationPrincipal CustomUserDetails user) {
        Long memberId = user.getMember().getMemberId();
        log.info("나의 추억 목록 조회 : memberId={}", memberId);
        List<MomentDTO> moments = momentService.getMyMoments(memberId);
        return ResponseDTO.ok(moments);
    }

    // 추억 상세 조회
    @GetMapping("/{momentId}")
    public ResponseDTO<MomentDTO> getMomentById(@PathVariable Long momentId) {
        log.info("상세 조회할 추억 id : momentId={}", momentId);
        MomentDTO moment = momentService.getMomentById(momentId);
        return ResponseDTO.ok(moment);
    }

    // 지도에서 추억 전체 조회(내 추억 + 공개 추억)
    @GetMapping("/all")
    public ResponseDTO<List<MomentDTO>> getAllMoments(@AuthenticationPrincipal CustomUserDetails user,
                                                      @RequestParam Double minLat,
                                                      @RequestParam Double maxLat,
                                                      @RequestParam Double minLng,
                                                      @RequestParam Double maxLng) {
        Long memberId = user.getMember().getMemberId();
        log.info("새롭게 검색된 데이터 : memberId={}, 범위=({}, {}), ({}, {})", memberId, minLat, maxLat, minLng, maxLng);
        List<MomentDTO> moments = momentService.getMomentsForMap(memberId, minLat, maxLat, minLng, maxLng);

        return ResponseDTO.ok(moments);

    }
}
