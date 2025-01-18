package beyond.momentours.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

// 들고 온(Request Header) 토큰이 유효한지 판별 및 인증(Authentication 객체로 관리)
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public JWTFilter(JWTUtil jwtUtil, JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.jwtUtil = jwtUtil;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // 특정 경로는 필터 적용 제외
        if (requestURI.equals("/api/member/login") || requestURI.equals("/api/member/signup")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 헤더에서 access키에 담긴 토큰을 꺼냄
        String accessToken = request.getHeader("Authorization");

        // "Bearer "와 공백을 제거
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7).trim(); // "Bearer " 제거 후 공백 제거
        } else {
            throw new IllegalArgumentException("Authorization header is missing or malformed.");
        }

        // 토큰이 없다면 다음 필터로 넘김
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            if (jwtUtil.isExpired(accessToken)) {
                log.debug("Token is expired");
                throw new ExpiredJwtException(null, null, "토큰이 만료되었습니다");
            }
        } catch (ExpiredJwtException e) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("만료된 access token 입니다.");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


        // 4. 토큰이 유효한 경우 인증 정보 설정
        try {
            Authentication authentication = jwtAuthenticationProvider.getAuthentication(accessToken);
            log.debug("Created authentication: {}", authentication); // 인증 객체 생성 확인
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Set authentication in SecurityContext");
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 응답
            response.getWriter().write("만료된 토큰입니다.");
            return;
        }

        // 5. 필터 체인의 다음 단계로 진행
        filterChain.doFilter(request, response);

    }
}
