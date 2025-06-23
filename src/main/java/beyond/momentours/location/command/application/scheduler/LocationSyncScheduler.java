package beyond.momentours.location.command.application.scheduler;

import beyond.momentours.location.command.domain.aggregate.LocationStatus;
import beyond.momentours.location.command.domain.aggregate.entity.Location;
import beyond.momentours.location.command.domain.repository.LocationRepository;
import beyond.momentours.location.query.external.NaverMapSearchClient;
import beyond.momentours.location.query.external.dto.NaverPlaceDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocationSyncScheduler {

    private final LocationRepository locationRepository;
    private final NaverMapSearchClient naverMapSearchClient;

    // 매월 1일 새벽 3시에 실행
    @Scheduled(cron = "0 0 3 1 * ?")
    public void syncLocationsWithNaver() {
        log.info("[장소 동기화] 네이버 지도와 장소 정보 동기화 시작");

        List<Location> locations = locationRepository.findAllByLocationStatus(LocationStatus.UNCHANGED);

        for (Location location : locations) {
            try {
                NaverPlaceDTO naverInfo = naverMapSearchClient.searchPlaceFirstMatch(location.getLocationName());

                if (naverInfo == null) {
                    location.setLocationStatus(LocationStatus.CLOSED);
                    log.info("폐업 처리: {}", location.getLocationName());
                } else if (!location.getLatitude().equals(naverInfo.getLatitude()) ||
                           !location.getLongitude().equals(naverInfo.getLongitude())) {
                    location.setLocationStatus(LocationStatus.RELOCATION);
                    log.info("이전 처리: {} (기존: {},{} / 네이버: {},{})",
                        location.getLocationName(),
                        location.getLatitude(), location.getLongitude(),
                        naverInfo.getLatitude(), naverInfo.getLongitude());
                } else {
                    location.setAddress(naverInfo.getAddress());
                    location.setDescription(naverInfo.getDescription());
                    location.setLocationStatus(LocationStatus.UNCHANGED);
                }
                locationRepository.save(location);
            } catch (Exception e) {
                log.error("장소 동기화 중 오류: {}", location.getLocationName(), e);
            }
        }
        log.info("[장소 동기화] 네이버 지도와 장소 정보 동기화 완료");
    }
} 