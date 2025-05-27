package beyond.momentours.location.query.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.location.query.service.LocationQueryService;
import beyond.momentours.location.query.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/cluster")
    public ResponseDTO<List<ResponseLocationClusterItemVO>> getClusteredLocations(@RequestParam BigDecimal latitude, @RequestParam BigDecimal longitude) {
        List<ResponseLocationClusterItemVO> result = locationQueryService.getClusteredLocations(latitude, longitude);
        return ResponseDTO.ok(result);
    }

    @GetMapping("/{locationId}/moments")
    public ResponseDTO<ResponseLocationMomentPageVO> getLocationWithMoments(@PathVariable Long locationId, @RequestParam(required = false) Long cursor, @RequestParam(defaultValue = "10") int size) {
        ResponseLocationMomentPageVO result = locationQueryService.getLocationWithMoments(locationId, cursor, size);
        return ResponseDTO.ok(result);
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
}
