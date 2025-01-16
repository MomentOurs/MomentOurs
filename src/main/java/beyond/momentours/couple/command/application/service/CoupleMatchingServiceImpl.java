package beyond.momentours.couple.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.couple.command.domain.aggregate.entity.CoupleList;
import beyond.momentours.couple.command.domain.aggregate.entity.MatchingCode;
import beyond.momentours.couple.command.domain.aggregate.entity.MatchingStatus;
import beyond.momentours.couple.command.domain.repository.CoupleRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
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
    // QRCodeWriter를 싱글톤으로 관리해 리소스 낭비 감소 및 이를 위한 변수 미리 선언
    private static final QRCodeWriter qrCodeWriter = new QRCodeWriter();
    private static final int QR_CODE_SIZE = 300;
    private static final String QR_CODE_FORMAT = "PNG";

    @Autowired
    public CoupleMatchingServiceImpl(RedisTemplate<String, MatchingCode> redisTemplate, CoupleRepository coupleRepository) {
        this.redisTemplate = redisTemplate;
        this.coupleRepository = coupleRepository;
    }

    @Override
    public byte[] createMatchingCode(Long userId) {
        // 0. 기존 코드가 있는지 확인
        String userKey = KEY_PREFIX + userId;
        MatchingCode existingCode = redisTemplate.opsForValue().get(userKey);

        // 만약 있다면 그대로 반환
        if (existingCode != null) {
            return createQRCode(existingCode.getId());
        }

        // 1. 없으면 새로운 MatchingCode 생성
        MatchingCode matchingCode = MatchingCode.create(userId);

        // 2. Redis에 매칭 코드 TTL 부여하면서 저장(2가지로)
        // 2-1. 사용자 ID로 조회 가능하도록
        redisTemplate.opsForValue().set(userKey, matchingCode, MATCHING_CODE_TTL);
        // 2-2. 매칭 코드로 조회 가능하도록
        redisTemplate.opsForValue().set(CODE_PREFIX + matchingCode.getId(), matchingCode, MATCHING_CODE_TTL);

        // 3. QR코드로 변환
        return createQRCode(matchingCode.getId());
    }

    @Override
    public byte[] createQRCode(String matchingCode) {
        try {
            // QR코드 내용 설정
            // 1. BitMatrix는 QR코드의 기본 구조를 나타내는 2차원 행렬. QR코드의 각 픽샐은 1(검은색), 0(흰색)으로 구성되어 있다.
            BitMatrix bitMatrix = qrCodeWriter.encode(
                    matchingCode, // 코드에 담을 데이터
                    BarcodeFormat.QR_CODE, // 바코드 형식 지정
                    QR_CODE_SIZE, // 가로(px)
                    QR_CODE_SIZE // 세로 크기(px)
            );

            // BitMatrix를 PNG로 변환하기 위해 사용
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(
                    bitMatrix, // 변환할 비트 행렬
                    QR_CODE_FORMAT, // 출력할 이미지 형식
                    baos); // 이미지 데이터를 담을 stream(한 번에 처리 대신 점진적 처리)

            return baos.toByteArray();

        } catch (WriterException | IOException e) {
            throw new CommonException(ErrorCode.QRCODE_CREATE_FAIL);
        }
    }

    public void authenticationMatchingCode(String matchingCode, Long requestUserId) {
        // 매칭 코드 검증 후 정보 가져옴
        MatchingCode validCode = validateAndGetMatchingCode(matchingCode, requestUserId);

        // 인증된 코드에서 회원번호 추출 후, 커플 생성
        createCouple(validCode.getUserId(), requestUserId);

        markMatchingCodeAsUsed(matchingCode);
    }

    // redis와 통신하여 QR코드를 조회하고 유효성 검사를 시도하는 메서드입니다.
    private MatchingCode validateAndGetMatchingCode(String matchingCode, Long requestUserId) {
        String key = CODE_PREFIX + matchingCode;

        return (MatchingCode) redisTemplate.execute(new SessionCallback<Object>() {
            @Override // 데이터 일관성을 보장하기 위한 redis execute method 사용
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi(); // multi transaction

                MatchingCode foundedCode = (MatchingCode) operations.opsForValue().get(key);

                // 각종 유효성 검사
                if (foundedCode == null) {
                    log.info("redis에서 조회된 QR코드가 없는 경우 발생하는 에러");
                    operations.discard();
                    throw new CommonException(ErrorCode.NOT_FOUND_QRCODE);
                }

                if (requestUserId.equals(foundedCode.getUserId())) {
                    log.info("요청자가 자신의 QR코드로 신청한 경우 발생하는 에러");
                    operations.discard();
                    throw new CommonException(ErrorCode.INVALID_CODE_REQUEST);
                }

                if (foundedCode.getMatchingStatus().equals(MatchingStatus.USED)) {
                    log.info("사용된 QR코드로 인증을 시도한 경우 발생하는 에러");
                    operations.discard();
                    throw new CommonException(ErrorCode.USED_CODE_REQUEST);
                }

                if (CheckCoupleStatus(requestUserId) || CheckCoupleStatus(foundedCode.getUserId())) {
                    log.info("상대방 혹은 자신이 커플인 상태에서 요청한 경우 발생하는 에러");
                    operations.discard();
                    throw new CommonException(ErrorCode.ALREADY_COUPLE_STATUS);
                }

                operations.exec();
                return foundedCode;
            }
        });
    }

    // DB와 연동하여 커플 상태로 전환하는 메서드입니다.
    private void createCouple(Long memberId1, Long memberId2) {

    }

    // 회원 번호를 기반으로 커플인지 검증하는 메서드입니다.
    private boolean CheckCoupleStatus(Long userId) {
        CoupleList coupleList = coupleRepository.findActiveCoupleByMemberId(userId).orElse(null);

        return coupleList != null;
    }

    // 사용된 QR코드로 마킹하는 메서드입니다.
    private void markMatchingCodeAsUsed(String matchingCode) {

    }
}
