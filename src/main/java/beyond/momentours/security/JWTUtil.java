package beyond.momentours.security;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JWTUtil {

    private final Key key;

    public JWTUtil(@Value("${jwt.secret-key}") String secret) {
        try {
            byte[] byteSecretKey = Decoders.BASE64.decode(secret);
            key = Keys.hmacShaKeyFor(byteSecretKey);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize JWTUtil with secret key: " + secret, e);
        }
    }

    private Collection<? extends GrantedAuthority> extractAuthorities(Claims claims) {
        if (claims.get("roles") == null || claims.get("roles").toString().isEmpty()) {
            throw new CommonException(ErrorCode.INVALID_TOKEN_ERROR);
        }
        return Arrays.stream(claims.get("roles").toString()
                        .replace("[", "").replace("]", "").split(", "))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getMemberId(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("memberEmail", String.class);
    }

    public String getRole(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    public String createJwt(String category, String memberEmail, String role, Long expiredMs) {

        Claims claims = Jwts.claims();
        claims.put("memberEmail", memberEmail);
        claims.put("role", role);

        return Jwts.builder()
                .claim("category", category)    // access 토큰인지 refresh 토큰인지
                .claim("memberEmail", memberEmail)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 클레임을 추출하는 메서드
    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key) // 서명 키를 설정
                    .build()
                    .parseClaimsJws(token) // 토큰 파싱
                    .getBody(); // 클레임 정보 반환
        } catch (SignatureException e) {
            throw new IllegalArgumentException("Invalid token signature", e);
        }
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("Token has expired: {}", token, e);
            throw e;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid token: {}", token, e);
            throw e;
        }
    }

    public String generateToken(String memberEmail, String role, String provider, Authentication authentication) {
        Claims claims = Jwts.claims();
        claims.put("memberEmail", memberEmail);
        claims.put("role", role);
        claims.put("provider", provider);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        ZoneId kst = ZoneId.of("Asia/Seoul");
        ZonedDateTime now = ZonedDateTime.now(kst);
        ZonedDateTime expirationDateTime = now.plusHours(3600000 / 1000 * 60 * 60);

        userDetails.setExpiration(expirationDateTime.toLocalDateTime());

        return  Jwts.builder()
                .claim("memberEmail", memberEmail)
                .claim("role", role)
                .claim("provider", provider)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(expirationDateTime.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration = getClaimFromToken(token, Claims::getExpiration);
        ZonedDateTime utcExpiration = expiration.toInstant().atZone(ZoneId.of("UTC"));
        ZonedDateTime kstExpiration = utcExpiration.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
        return Date.from(kstExpiration.toInstant());
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateRefreshToken(String memberEmail) {
        Claims claims = Jwts.claims();
        claims.put("memberEmail", memberEmail);

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        ZonedDateTime expirationDateTime = now.plusDays(7);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(expirationDateTime.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}


