package beyond.momentours.util;

import jakarta.servlet.http.HttpServletRequest;

public class SecurityUtil {

    // Authorization 헤더에서 JWT 토큰 추출
    public static String extractToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7).trim(); // "Bearer " 제거 후 반환
        }
        return null;
    }

}
