package beyond.momentours.couplelog.command.application.service;

import beyond.momentours.member.command.application.dto.CustomUserDetails;

public interface CursorTrackingService {
    void startUpdating(Long couplelogId, CustomUserDetails user);
}
