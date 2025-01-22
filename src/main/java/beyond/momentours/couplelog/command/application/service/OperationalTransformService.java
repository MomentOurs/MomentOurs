package beyond.momentours.couplelog.command.application.service;

import beyond.momentours.couplelog.command.domain.aggregate.session.EditOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OperationalTransformService {

    public EditOperation transform(EditOperation clientOp, EditOperation serverOp) {
        // 서버 작업이 먼저 발생한 경우에만 변환
        if (clientOp.getTimestamp() > serverOp.getTimestamp()) {
            return clientOp;
        }

        log.debug("작업 변환 시작: clientOp={}, serverOp={}", clientOp, serverOp);

        EditOperation transformedOp = EditOperation.builder()
                .operationType(clientOp.getOperationType())
                .content(clientOp.getContent())
                .memberId(clientOp.getMemberId())
                .timestamp(clientOp.getTimestamp())
                .build();

        // 위치 조정 로직
        if (serverOp.getOperationType().equals(EditOperation.OperationType.INSERT)) {
            transformedOp.setPosition(caculateInsertPosition(
                    clientOp.getPosition(),
                    serverOp.getPosition(),
                    serverOp.getContent().length()
            ));
        } else {
            transformedOp.setPosition(caculateDeletePosition(
                    clientOp.getPosition(),
                    serverOp.getPosition(),
                    serverOp.getContent().length()
            ));
        }

        log.debug("작업 변환 결과: transformedOp={}", transformedOp);
        return transformedOp;
    }

    private int caculateDeletePosition(int clientPos, int serverPos, int length) {
        return clientPos > serverPos ? serverPos + length : clientPos;
    }

    private int caculateInsertPosition(int clientPos, int serverPos, int length) {
        return clientPos > serverPos ? serverPos - length : clientPos;
    }
}
