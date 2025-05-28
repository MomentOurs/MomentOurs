package beyond.momentours.location.common.util;

import java.util.Arrays;
import java.util.List;

public final class LocationFilterUtil {

    private LocationFilterUtil() {
        throw new UnsupportedOperationException("Utility class");
    }
    private static final List<String> EXCLUDED_KEYWORDS = Arrays.asList(
            "초등학교", "중학교", "고등학교", "대학교", "학교",
            "아파트", "빌라", "오피스텔", "공장", "창고", "고물상",
            "주유소", "정비소", "철물점", "택배", "마트", "편의점", "부동산",
            "병원", "약국", "의원", "내과", "치과",
            "공업사", "정수장", "하수처리장", "폐기물",
            "주차장", "공영주차장", "구청", "동사무소", "파출소",
            "세탁소", "세차장", "자동차", "영업소", "공구상", "펌프장",
            "공영", "검문소", "수도사업소"
    );

    public static boolean isExcludedPlace(String placeName) {
        return EXCLUDED_KEYWORDS.stream().anyMatch(placeName::contains);
    }
}
