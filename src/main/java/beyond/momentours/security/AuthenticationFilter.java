package beyond.momentours.security;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.member.command.application.dto.JwtTokenDTO;
import beyond.momentours.member.command.application.service.LoginHistoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final LoginHistoryService loginHistoryService;

    public AuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, LoginHistoryService loginHistoryService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.loginHistoryService = loginHistoryService;
        setFilterProcessesUrl("/api/member/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String requestBody = "";

        try {
            // 요청 본문을 읽음
            requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
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

        if (!(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            throw new CommonException(ErrorCode.INVALID_AUTHENTICATION_OBJECT);
        }

        String memberEmail = userDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String token = jwtUtil.generateToken(memberEmail, role, null, authentication);

        Date expirationDate = jwtUtil.getExpirationDateFromToken(token);
        ZonedDateTime kstExpiration = ZonedDateTime.ofInstant(expirationDate.toInstant(), ZoneId.of("Asia/Seoul"));

        int[] expArray = new int[] {
                kstExpiration.getYear(),
                kstExpiration.getMonthValue(),
                kstExpiration.getDayOfMonth(),
                kstExpiration.getHour(),
                kstExpiration.getMinute(),
                kstExpiration.getSecond()
        };

        String refreshToken = jwtUtil.generateRefreshToken(memberEmail);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("accessToken", token);
        responseData.put("refreshToken", refreshToken);
        responseData.put("expiration", expArray);
        responseData.put("memberEmail", memberEmail);

        Long memberId = userDetails.getMemberId();
        try {
            loginHistoryService.recordLogin(memberId, request.getRemoteAddr());
        } catch (Exception e) {
            throw new CommonException(ErrorCode.LOGIN_HISTORY_FAILURE);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseData));

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

