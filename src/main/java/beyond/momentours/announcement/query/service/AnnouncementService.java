package beyond.momentours.announcement.query.service;
import beyond.momentours.announcement.query.dto.AnnouncementDTO;

import java.util.List;

public interface AnnouncementService {

    // 1. 모든 공지사항 조회
    List<AnnouncementDTO> findAllAnnouncements();

    // 2. id로 공지사항 조회
    AnnouncementDTO findAnnouncementById(Long id);

    // 3. 키워드로 공지사항 조회
    List<AnnouncementDTO> findAnnouncementByKeyword(String keyword);

    // 4. 작성자id로 공지사항 조회
    List<AnnouncementDTO> findAnnouncementByMemberId(Long memberId);



}
