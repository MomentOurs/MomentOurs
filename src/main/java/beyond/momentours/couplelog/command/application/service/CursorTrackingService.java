package beyond.momentours.couplelog.command.application.service;

public interface CursorTrackingService {
    void startUpdating(Long couplelogId, Long memberId);
}
