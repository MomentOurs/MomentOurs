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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@Slf4j
public class CoupleMatchingServiceImpl implements CoupleMatchingService {
    private final RedisTemplate<String, MatchingCode> redisTemplate;
    private final CoupleRepository coupleRepository;
    private static final String KEY_PREFIX = "matching_user:";
    private static final String CODE_PREFIX = "matching_code:";
    private static final Duration MATCHING_CODE_TTL = Duration.ofHours(6);

    @Autowired
    public CoupleMatchingServiceImpl(RedisTemplate<String, MatchingCode> redisTemplate, CoupleRepository coupleRepository) {
        this.redisTemplate = redisTemplate;
        this.coupleRepository = coupleRepository;
    }

    @Override
    public MatchingCodeDTO createMatchingCode(Long userId) {
        // 0. 기존 코드가 있는지 확인
        String userKey = KEY_PREFIX + userId;
        MatchingCode existingCode = redisTemplate.opsForValue().get(userKey);

        // 만약 있다면 그대로 반환
        if (existingCode != null) {
            return MatchingCodeDTO.builder()
                    .id(existingCode.getId())
                    .memberId(existingCode.getMemberId())
                    .createdAt(existingCode.getCreatedAt())
                    .matchingStatus(existingCode.getMatchingStatus())
                    .build();
        }

        // 1. 없으면 새로운 MatchingCode 생성
        MatchingCode matchingCode = MatchingCode.create(userId);

        // 2. Redis에 매칭 코드 TTL 부여하면서 저장(2가지로)
        // 2-1. 사용자 ID로 조회 가능하도록
        redisTemplate.opsForValue().set(userKey, matchingCode, MATCHING_CODE_TTL);
        // 2-2. 매칭 코드로 조회 가능하도록
        redisTemplate.opsForValue().set(CODE_PREFIX + matchingCode.getId(), matchingCode, MATCHING_CODE_TTL);

        // 3. QR코드로 변환
        return MatchingCodeDTO.builder()
                .id(matchingCode.getId())
                .memberId(matchingCode.getMemberId())
                .createdAt(matchingCode.getCreatedAt())
                .matchingStatus(matchingCode.getMatchingStatus())
                .build();
    }

    @Override
    public CoupleListDTO authenticationMatchingCode(String matchingCode, Long requestMemberId) {
        // 매칭 코드 검증 후 정보 가져옴
        MatchingCode validCode = validateAndGetMatchingCode(matchingCode, requestMemberId);

        // 인증된 코드에서 회원번호 추출 후, 커플 생성
        // code 속 회원번호는 memberId1에, 인증 요청 회원번호는 memberId2에 입력
        CoupleListDTO coupleListDTO = createCouple(validCode.getMemberId(), requestMemberId);

        // 사용된 매칭 코드는 사용됨으로 마킹
        markMatchingCodeAsUsed(matchingCode);

        return coupleListDTO;
    }

    // redis와 통신하여 QR코드를 조회하고 유효성 검사를 시도하는 메서드입니다.
    private MatchingCode validateAndGetMatchingCode(String matchingCode, Long requestMemberId) {
        String key = CODE_PREFIX + matchingCode;
        log.info("validateAndGetMatchingCode method 시작");
        return (MatchingCode) redisTemplate.execute(new SessionCallback<Object>() {
            @Override // 데이터 일관성을 보장하기 위한 redis execute method 사용
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi(); // multi transaction
                log.info("매칭 코드 검증을 위한 redis transaction 시작");
                MatchingCode foundedCode = (MatchingCode) operations.opsForValue().get(key);

                // 각종 유효성 검사
                if (foundedCode == null) {
                    log.info("redis에서 조회된 QR코드가 없는 경우 발생하는 에러");
                    operations.discard();
                    throw new CommonException(ErrorCode.NOT_FOUND_CODE);
                }

                if (requestMemberId.equals(foundedCode.getMemberId())) {
                    log.info("요청자가 자신의 QR코드로 신청한 경우 발생하는 에러");
                    operations.discard();
                    throw new CommonException(ErrorCode.INVALID_CODE_REQUEST);
                }

                if (foundedCode.getMatchingStatus().equals(MatchingStatus.USED)) {
                    log.info("사용된 QR코드로 인증을 시도한 경우 발생하는 에러");
                    operations.discard();
                    throw new CommonException(ErrorCode.USED_CODE_REQUEST);
                }

                if (CheckCoupleStatus(requestMemberId) || CheckCoupleStatus(foundedCode.getMemberId())) {
                    log.info("상대방 혹은 자신이 커플인 상태에서 요청한 경우 발생하는 에러");
                    operations.discard();
                    throw new CommonException(ErrorCode.ALREADY_COUPLE_STATUS);
                }

                log.info("매칭코드 유효성 검사를 위한 redis transaction 종료");
                operations.exec();
                return foundedCode;
            }
        });
    }

    // DB와 연동하여 커플 상태로 전환하는 메서드입니다.
    private CoupleListDTO createCouple(Long memberId1, Long memberId2) {
        log.info("커플 생성 메서드 시작");
        // 회원 서비스에서 회원번호 기반 이름 닉네임 조회

        CoupleList newCouple = new CoupleList();
        newCouple.setCoupleName("회원 서비스에서 양쪽 회원 이름을 조회해서 붙여넣을 예정");
        newCouple.setMemberId1(memberId1);
        newCouple.setMemberId2(memberId2);
        newCouple.setCoupleMatchingStatus(CoupleMatchingStatus.AUTHENTICATED);
        newCouple.setCoupleStatus(false);
        newCouple.setCoupleStartDate(LocalDateTime.now());
        log.info("생성된 커플 정보 newCouple: {}", newCouple);
        coupleRepository.save(newCouple);

        CoupleListDTO coupleListDTO = CoupleListDTO.builder()
                .coupleId(newCouple.getCoupleId())
                .coupleName(newCouple.getCoupleName())
                .couplePhoto(newCouple.getCouplePhoto())
                .coupleStartDate(newCouple.getCoupleStartDate())
                .coupleMatchingStatus(newCouple.getCoupleMatchingStatus())
                .coupleStatus(newCouple.getCoupleStatus())
                .memberId1(newCouple.getMemberId1())
                .memberId2(newCouple.getMemberId2())
                .build();

        return coupleListDTO;
    }

    // 회원 번호를 기반으로 커플인지 검증하는 메서드입니다.
    private boolean CheckCoupleStatus(Long userId) {
        log.info("회원 번호 기반 커플 검증 메서드 시작");
        CoupleList coupleList = coupleRepository.findActiveCoupleByMemberId(userId).orElse(null);

        return coupleList != null;
    }

    // 사용된 코드로 마킹하는 메서드입니다.
    public void markMatchingCodeAsUsed(String matchingCode) {
        String key = CODE_PREFIX + matchingCode;
        log.info("사용된 매칭코드 마킹 메서드 시작");
        redisTemplate.execute(new SessionCallback<Object>() {
           @Override
           public Object execute(RedisOperations operations) throws DataAccessException {
               operations.multi();
               log.info("사용된 매칭코드 마킹을 위한 redis transaction 시작");

               MatchingCode foundedCode = (MatchingCode) operations.opsForValue().get(key);
               if (foundedCode == null) {
                   log.info("마킹 하기 전에 만료가 되어 redis에서 삭제된 경우 발생하는 에러");
                   operations.discard();
                   throw new CommonException(ErrorCode.NOT_FOUND_CODE);
               }
               Long remainingTTL = operations.getExpire(key, TimeUnit.MILLISECONDS);
               log.info("redis에 저장된 코드의 남은 TTL: {}", remainingTTL);

               if (remainingTTL > 0) {
                   foundedCode.setMatchingStatus(MatchingStatus.USED);
                   operations.opsForValue().set(key, foundedCode, remainingTTL, TimeUnit.MILLISECONDS);
               }
               else {
                   log.info("redis에 저장된 코드의 ttl이 만료된 경우(음수) 발생하는 에러");
                   operations.discard();
                   throw new CommonException(ErrorCode.EXPIRED_CODE);
               }

               log.info("사용된 매칭코드 마킹을 위한 redis transaction 종료");
               return operations.exec();
           }
        });
    }
}
