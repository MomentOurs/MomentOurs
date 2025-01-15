package beyond.momentours.couple.command.application.service;

public interface CoupleMatchingService {
    byte[] createMatchingCode(Long userId);
    byte[] createQRCode(String matchingCode);
}
