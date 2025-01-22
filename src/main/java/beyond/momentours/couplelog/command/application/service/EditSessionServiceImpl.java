package beyond.momentours.couplelog.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.couplelog.command.domain.aggregate.session.EditOperation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class EditSessionServiceImpl implements EditSessionService {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String EDITING_KEY_PREFIX = "editing:";
    private static final String OPERATION_KEY_PREFIX = "operation:";

    @Autowired
    public EditSessionServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void startEditing(Long couplelogId, Long memberId) {
        String editingKey = EDITING_KEY_PREFIX + couplelogId;
        String currentEditor = redisTemplate.opsForValue().get(editingKey);

        // 이미 다른 사용자가 편집 중인지 확인
        if (currentEditor != null && !currentEditor.equals(memberId.toString())) {
            log.error("이미 다른 사용자가 편집 중: coupleLogId={}, currentEditor={}", couplelogId, currentEditor);
            throw new CommonException(ErrorCode.FORBIDDEN_ROLE);
        }

        // 편집 세션 시작
        redisTemplate.opsForValue().set(editingKey, memberId.toString(), 30, TimeUnit.MINUTES);
        log.info("편집 세션 시작: coupleLogId={}, memberId={}", couplelogId, memberId);
    }

    @Override
    public void saveOperation(Long CouplelogId, EditOperation operation) {
        String operationKey = OPERATION_KEY_PREFIX + CouplelogId;
        try {
            String operationJson = new ObjectMapper().writeValueAsString(operation);
            redisTemplate.opsForValue().set(operationKey, operationJson, 30, TimeUnit.MINUTES);
        } catch (JsonProcessingException e) {
            log.error("커플로그 세션의 operation Json Processing 중 예외 에러 발생: {}", e.getMessage());
            throw new CommonException(ErrorCode.BAD_REQUEST_JSON);
        }
    }

    @Override
    public EditOperation getLastOperation(Long CouplelogId) throws JsonProcessingException {
        String operationKey = OPERATION_KEY_PREFIX + CouplelogId;
        String operationJson = redisTemplate.opsForValue().get(operationKey);

        if (operationJson == null) {
            return null;
        }

        return new ObjectMapper().readValue(operationJson, EditOperation.class);
    }

    @Override
    public void endEditSession(Long couplelogId) {
        String editingKey = EDITING_KEY_PREFIX + couplelogId;
        String operationKey = OPERATION_KEY_PREFIX + couplelogId;

        redisTemplate.delete(editingKey);
        redisTemplate.delete(operationKey);

        log.info("편집 세션 종료: couplelogId={}", couplelogId);
    }
}
