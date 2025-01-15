package beyond.momentours.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

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

    public String getMemberId(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("memberEmail", String.class);
    }

    public String getRole(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("role", String.class);
    }

    public String getCategory(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("category", String.class);
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

    // memberId 추출하는 메서드
//    public Long getMemberId(String token) {
//        Claims claims = getClaims(token);
//        return Long.valueOf(claims.get("memberId").toString());
//    }
}

