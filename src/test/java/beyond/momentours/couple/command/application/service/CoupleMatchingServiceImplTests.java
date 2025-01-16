package beyond.momentours.couple.command.application.service;

import beyond.momentours.couple.command.application.dto.MatchingCodeDTO;
import beyond.momentours.couple.command.domain.aggregate.entity.MatchingCode;
import beyond.momentours.couple.command.domain.aggregate.entity.MatchingStatus;
import beyond.momentours.couple.command.domain.repository.CoupleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
class CoupleMatchingServiceImplTests {
    @Mock
    private RedisTemplate<String, MatchingCode> redisTemplate;

    @Mock
    private CoupleRepository coupleRepository;

    @Mock
    private ValueOperations<String, MatchingCode> valueOperations;

    @InjectMocks
    private CoupleMatchingServiceImpl coupleMatchingService;

    @BeforeEach
    void setUp() {
        // redisTemplate의 opsForValue() 메서드가 호출될 때마다 mock ValueOperations를 반환하도록 설정
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    @DisplayName("새로운 매칭 코드 생성 테스트")
    void createNewMatchingCode() {
        // given
        Long memberId = 1L;
        String userKey = "matching_user:" + memberId;

        // redis에 저장할 매칭 코드가 없는 상황 가정
        when(valueOperations.get(userKey)).thenReturn(null);

        // when
        MatchingCodeDTO result = coupleMatchingService.createMatchingCode(memberId);

        // then
        assertNotNull(result);
        assertEquals(memberId, result.getMemberId());
        assertEquals(MatchingStatus.PENDING, result.getMatchingStatus());

        // redis 저장 검증 (userKey(Key) - MatchingCode(Value), TTL(6H))
        verify(valueOperations).set(
                eq(userKey),
                any(MatchingCode.class),
                eq(Duration.ofHours(6))
        );
    }
}