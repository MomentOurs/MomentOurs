package beyond.momentours.couple.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.couple.command.domain.aggregate.entity.MatchingCode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;

@Service
@Transactional
@Slf4j
public class CoupleMatchingServiceImpl implements CoupleMatchingService {
    private final RedisTemplate<String, MatchingCode> redisTemplate;
    private static final String KEY_PREFIX = "matching_code:";
    private static final Duration MATCHING_CODE_TTL = Duration.ofHours(6);
    // QRCodeWriter를 싱글톤으로 관리해 리소스 낭비 감소 및 이를 위한 변수 미리 선언
    private static final QRCodeWriter qrCodeWriter = new QRCodeWriter();
    private static final int QR_CODE_SIZE = 300;
    private static final String QR_CODE_FORMAT = "PNG";

    @Autowired
    public CoupleMatchingServiceImpl(RedisTemplate<String, MatchingCode> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public byte[] createMatchingCode(Long userId) {
        // 1. MatchingCode 생성
        MatchingCode matchingCode = MatchingCode.create(userId);

        // 2. Redis에 매칭 코드 TTL 부여하면서 저장
        String key = KEY_PREFIX + matchingCode.getId();
        redisTemplate.opsForValue().set(key, matchingCode, MATCHING_CODE_TTL);

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
}
