package beyond.momentours.location.query.external;

import beyond.momentours.location.query.external.dto.NaverPlaceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class NaverMapSearchClientImpl implements NaverMapSearchClient {

    @Override
    public List<NaverPlaceDTO> searchPlaces(String keyword) {
        // TODO: 실제 네이버 API 호출 구현
        log.info("네이버 장소 검색 실행: {}", keyword);

        // 임시 Mock 반환 (실제 구현 전까지)
        return Collections.emptyList();
    }
}
