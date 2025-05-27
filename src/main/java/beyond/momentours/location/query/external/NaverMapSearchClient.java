package beyond.momentours.location.query.external;

import beyond.momentours.location.query.external.dto.NaverPlaceDTO;

import java.util.List;

public interface NaverMapSearchClient {
    List<NaverPlaceDTO> searchPlaces(String keyword);

}
