package beyond.momentours.couple.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.couple.command.application.dto.CoupleListDTO;
import beyond.momentours.couple.command.application.dto.MatchingCodeDTO;
import beyond.momentours.couple.command.domain.aggregate.entity.CoupleList;
import beyond.momentours.couple.command.domain.aggregate.entity.CoupleMatchingStatus;
import beyond.momentours.couple.command.domain.aggregate.entity.MatchingCode;
import beyond.momentours.couple.command.domain.aggregate.entity.MatchingStatus;
import beyond.momentours.couple.command.domain.repository.CoupleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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

    @Test
    @DisplayName("새로운 매칭 코드 생성 테스트")
    void createNewMatchingCode() {
        // given
        Long memberId = 1L;
        String userKey = "matching_user:" + memberId;

        // redisTemplate의 opsForValue() 메서드가 호출될 때 mock ValueOperations를 반환하도록 설정
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
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

    @Test
    @DisplayName("기존 매칭 코드가 있는 경우 테스트")
    void createNewMatchingCode_ExistingCode() {
        // given
        Long memberId = 1L;
        String userKey = "matching_user:" + memberId;
        MatchingCode existingCode = MatchingCode.create(memberId);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        // redis에 존재하는 코드를 반환했다고 가정
        when(valueOperations.get(userKey)).thenReturn(existingCode);

        // when
        MatchingCodeDTO result = coupleMatchingService.createMatchingCode(memberId);

        // then
        assertNotNull(result);
        assertEquals(existingCode.getId(), result.getId());
        assertEquals(memberId, result.getMemberId());

        // redis에 새로 저장하지 않았음을 검증
        verify(valueOperations, never()).set(
                eq(userKey),
                any(MatchingCode.class),
                any(Duration.class)
        );
    }

    @Test
    @DisplayName("매칭 코드 인증 성공 테스트")
    void authenticationMatchingCode_Success() {
        // given
        String matchingCode = "matching_code";
        Long requestMemberId = 1L;
        Long originalMemberId = 2L;

        MatchingCode validCode = MatchingCode.create(originalMemberId);
        CoupleList testCouple = new CoupleList();
        testCouple.setMemberId1(originalMemberId);
        testCouple.setMemberId2(requestMemberId );

        // redis transaction을 모의하기 위한 설정
        // validateAndGetMatchingCode 통과한 결과
        when(redisTemplate.execute(any(SessionCallback.class))).thenReturn(validCode);

        when(coupleRepository.save(any(CoupleList.class))).thenReturn(testCouple);

        // when
        CoupleListDTO resultDto = coupleMatchingService.authenticationMatchingCode(matchingCode, requestMemberId);

        // then
        assertNotNull(resultDto);
        assertEquals(originalMemberId, resultDto.getMemberId1());
        assertEquals(requestMemberId, resultDto.getMemberId2());
        assertEquals(CoupleMatchingStatus.AUTHENTICATED, resultDto.getCoupleMatchingStatus());
    }

    @Test
    @DisplayName("사용된 코드로 인증 시도 테스트")
    void authenticationMatchingCode_UsedCode() {
        // given
        String matchingCode = "matching_code";
        Long requestMemberId = 2L;

        // redis에서 사용된 코드를 반환하도록 설정
        when(redisTemplate.execute(any(SessionCallback.class)))
                .thenThrow(new CommonException(ErrorCode.USED_CODE_REQUEST));

        // when & then
        assertThrows(
                CommonException.class,
                () -> coupleMatchingService.authenticationMatchingCode(matchingCode, requestMemberId)
        );
    }

    @Test
    @DisplayName("Redis Transaction 테스트")
    void testRedisTransaction() {
        // given
        String matchingCode = "test_code";
        String key = "matching_code:" + matchingCode;

        RedisOperations<String, MatchingCode> operations = mock(RedisOperations.class);
        ValueOperations<String, MatchingCode> valueOps = mock(ValueOperations.class);

        // mock 객체들 동작 설정
        when(operations.opsForValue()).thenReturn(valueOps);
        MatchingCode foundedCode = MatchingCode.create(1L);
        when(valueOps.get(key)).thenReturn(foundedCode);
        when(operations.getExpire(key, TimeUnit.MILLISECONDS))
                .thenReturn(3600000L);

        when(redisTemplate.execute(any(SessionCallback.class))).thenAnswer(invocation -> {
            // SessionCallback 객체 가져옴(sessioncallback은 트랜잭션을 보장함으로서 데이터 무결성 유지에 도움이 됩니다)
            SessionCallback<?> sessionCallback = invocation.getArgument(0);
            // 해당 callback의 execute 메서드를 RedisOperations와 함께 실행
            return sessionCallback.execute(operations);
        });

        // when
        coupleMatchingService.markMatchingCodeAsUsed(matchingCode);

        // then
        verify(operations).multi(); // 트랜잭션 시작 검증
        verify(operations).exec(); // 트랜잭션 완료 검증
        verify(valueOps).set(
                eq(key),
                argThat(code -> code.getMatchingStatus().equals(MatchingStatus.USED)), // 사용된 코드로 바뀌었는지 검증
                eq(3600000L),
                eq(TimeUnit.MILLISECONDS)
        );
    }
}