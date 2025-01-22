package beyond.momentours.couplelog.command.application.service;

import beyond.momentours.couplelog.command.domain.aggregate.session.EditOperation;
import beyond.momentours.couplelog.command.domain.vo.response.EditSessionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface EditSessionService {
    void startEditing(Long couplelogId, Long memberId);

    EditOperation getLastOperation(Long CouplelogId) throws JsonProcessingException;

    void endEditSession(Long couplelogId);

    void saveOperation(Long couplelogId, EditOperation transformedOp);
}
