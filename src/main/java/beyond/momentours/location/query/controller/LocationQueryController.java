package beyond.momentours.location.query.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.location.query.external.NaverMapSearchClient;
import beyond.momentours.location.query.external.dto.NaverPlaceDTO;
import beyond.momentours.location.query.service.LocationQueryService;
import beyond.momentours.location.query.vo.*;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.moment.common.MomentFilterCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/location")
@RequiredArgsConstructor
public class LocationQueryController {

    private final LocationQueryService locationQueryService;
    private final NaverMapSearchClient naverMapSearchClient;

    @GetMapping("/map")
    public ResponseDTO<List<ResponseLocationMapVO>> getLocationsOnMap(@RequestParam BigDecimal latitudeMin, @RequestParam BigDecimal latitudeMax, @RequestParam BigDecimal longitudeMin, @RequestParam BigDecimal longitudeMax) {
        List<ResponseLocationMapVO> result = locationQueryService.getLocationsInBounds(latitudeMin, latitudeMax, longitudeMin, longitudeMax);
        return ResponseDTO.ok(result);
    }

    @GetMapping("/cluster")
    public ResponseDTO<List<ResponseLocationClusterItemVO>> getClusteredLocations(@RequestParam BigDecimal latitude, @RequestParam BigDecimal longitude) {
        List<ResponseLocationClusterItemVO> result = locationQueryService.getClusteredLocations(latitude, longitude);
        return ResponseDTO.ok(result);
    }

    @GetMapping("/{locationId}/moments")
    public ResponseDTO<ResponseLocationMomentPageVO> getLocationWithMoments(
            @PathVariable Long locationId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "recent") String sort,
            @RequestParam(required = false) Boolean onlyMine,
            @RequestParam(required = false) Boolean certifiedOnly,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long memberId = user != null ? user.getMember().getMemberId() : null;
        MomentFilterCondition condition = getMomentFilterCondition(locationId, cursor, size, sort, onlyMine, certifiedOnly, memberId);

        ResponseLocationMomentPageVO result = locationQueryService.getLocationWithMoments(condition);
        return ResponseDTO.ok(result);
    }

    private MomentFilterCondition getMomentFilterCondition(Long locationId, Long cursor, int size, String sort, Boolean onlyMine, Boolean certifiedOnly, Long memberId) {
        return MomentFilterCondition.builder()
                .locationId(locationId)
                .cursor(cursor)
                .size(size)
                .sort(sort)
                .onlyMine(onlyMine)
                .certifiedOnly(certifiedOnly)
                .memberId(memberId)
                .build();
    }

    @GetMapping("/cluster/grouped")
    public ResponseDTO<List<ResponseLocationClusterGroupVO>> getGroupedClusterMarkers(@RequestParam int zoom) {
        List<ResponseLocationClusterGroupVO> result = locationQueryService.getGroupedLocationClustersByZoom(zoom);
        return ResponseDTO.ok(result);
    }

    @GetMapping("/search")
    public ResponseDTO<List<ResponseLocationSearchVO>> searchLocation(@RequestParam String keyword) {
        List<ResponseLocationSearchVO> result = locationQueryService.searchLocation(keyword);
        return ResponseDTO.ok(result);
    }

    @GetMapping("/geohash")
    public ResponseDTO<List<ResponseLocationMapVO>> getByGeoHashPrefix(@RequestParam BigDecimal latitude, @RequestParam BigDecimal longitude, @RequestParam int zoom) {
        List<ResponseLocationMapVO> result = locationQueryService.findByGeoHashPrefix(latitude, longitude, zoom);
        return ResponseDTO.ok(result);
    }

    @GetMapping("/near")
    public ResponseDTO<List<ResponseLocationMapVO>> getNearbyLocations(@RequestParam BigDecimal latitude, @RequestParam BigDecimal longitude, @RequestParam(defaultValue = "1000") int radiusMeters) {
        List<ResponseLocationMapVO> result = locationQueryService.findNearby(latitude, longitude, radiusMeters);
        return ResponseDTO.ok(result);
    }

    @GetMapping("/recommended")
    public ResponseDTO<List<ResponseLocationMapVO>> getRecommendedNearby(@RequestParam BigDecimal latitude, @RequestParam BigDecimal longitude, @RequestParam(defaultValue = "1000") int radiusMeters, @RequestParam(defaultValue = "10") int limit) {
        List<ResponseLocationMapVO> result = locationQueryService.findRecommendedNearby(latitude, longitude, radiusMeters, limit);
        return ResponseDTO.ok(result);
    }

    @GetMapping("/{locationId}/detail")
    public ResponseDTO<ResponseLocationDetailVO> getLocationDetail(@PathVariable Long locationId) {
        ResponseLocationDetailVO result = locationQueryService.getLocationDetail(locationId);
        return ResponseDTO.ok(result);
    }

    @GetMapping("/naver-detail")
    public ResponseDTO<ResponseLocationDetailVO> getLocationFromNaver(@RequestParam String query) {
        NaverPlaceDTO dto = naverMapSearchClient.searchPlaceFirstMatch(query); // 최상단 하나만
        if (dto == null) throw new CommonException(ErrorCode.NOT_FOUND_LOCATION);

        return ResponseDTO.ok(ResponseLocationDetailVO.builder()
                .locationName(dto.getTitle())
                .address(dto.getAddress())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .momentCount(0)
                .totalLike(0)
                .totalView(0)
                .build());
    }
}
