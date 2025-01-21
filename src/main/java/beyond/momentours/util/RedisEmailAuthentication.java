package beyond.momentours.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class RedisEmailAuthentication {
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public RedisEmailAuthentication(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String checkEmailAuthentication(String key) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(key, "auth");
    }

    // 저장된 인증 코드 확인
    public String getEmailAuthenticationCode(String key) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(key, "code");
    }

    // 인증 코드 저장 및 만료 시간 설정
    public void setEmailAuthenticationExpire(String email, String code, long duration) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(email, "code", code);
        redisTemplate.expire(email, Duration.ofMinutes(duration));
    }

    // 인증 번호 만료 여부 확인
    public boolean isAuthenticationExpired(String email) {
        Long expireTime = redisTemplate.getExpire(email); // 남은 TTL 확인
        return expireTime == null || expireTime <= 0; // 만료된 경우 true 반환
    }


    // 인증 기록 삭제
    public void deleteEmailAuthenticationHistory(String key) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, "code");
    }

    // 인증 번호 검증
    public boolean verifyEmailAuthentication(String email, String code) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String storedCode = hashOperations.get(email, "code");
        storedCode = storedCode.replaceAll("[\\s\\x00-\\x1F]", "");

        if (storedCode == null) {
            return false;
        }

        boolean isValid = storedCode.trim().equals(code);

        return isValid;
    }
}

