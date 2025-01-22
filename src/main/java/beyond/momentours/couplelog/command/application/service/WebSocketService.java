package beyond.momentours.couplelog.command.application.service;

import beyond.momentours.member.command.application.dto.CustomUserDetails;

public interface WebSocketService {
    void registerEditor(Long couplelogId, CustomUserDetails user);
}
