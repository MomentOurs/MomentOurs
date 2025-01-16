package beyond.momentours.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public AuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/member/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String requestBody = "";

        try {
            // 요청 본문을 읽음
            requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println("Request Body: " + requestBody); // 요청 데이터 출력
        } catch (IOException e) {
            e.printStackTrace();
        }

        // JSON 데이터를 파싱하여 username과 password를 추출
        String username = null;
        String password = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper(); // Jackson 라이브러리 사용
            Map<String, String> requestData = objectMapper.readValue(requestBody, new TypeReference<Map<String, String>>() {});
            username = requestData.get("username");
            password = requestData.get("password");
        } catch (Exception e) {
            throw new AuthenticationServiceException("Invalid request body format");
        }

        // 디버깅 출력
        System.out.println("Extracted Username: " + username);
        System.out.println("Extracted Password: " + password);

        // username 또는 password가 없을 경우 예외 처리
        if (username == null || password == null) {
            throw new AuthenticationServiceException("Username or password is missing");
        }

        // UsernamePasswordAuthenticationToken 생성
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        // AuthenticationManager를 통해 인증
        return authenticationManager.authenticate(authToken);
    }


    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {

        //유저 정보
        String username = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //토큰 생성
        String access = jwtUtil.createJwt("access", username, role, 600000L);
        String refresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        //응답 설정
        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        response.setContentType("application/json;charset=UTF-8");      // JSON 형태로 응답
        response.setStatus(HttpStatus.OK.value());

        // JSON 데이터 작성
        String jsonResponse = String.format(
                "{\"accessToken\":\"%s\", \"refreshToken\":\"%s\"}",
                access,
                refresh
        );

        // 응답 전송
        response.getWriter().write(jsonResponse);

    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        response.setStatus(401);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        //cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}

