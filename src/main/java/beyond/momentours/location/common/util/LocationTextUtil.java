package beyond.momentours.location.common.util;

public class LocationTextUtil {
    public static String normalizeKeyword(String input) {
        if (input == null) return "";
        return input.replaceAll("\\s+", "")
                .replaceAll("[^가-힣a-zA-Z0-9]", "")
                .toLowerCase();
    }
}
