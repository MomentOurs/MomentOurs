package beyond.momentours.announcement.query.service;

import beyond.momentours.announcement.query.dto.AnnouncementDTO;
import beyond.momentours.announcement.query.repository.AnnouncementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementQueryServiceImpl implements AnnouncementQueryService {

    private final AnnouncementMapper announcementMapper;

    @Autowired
    public AnnouncementQueryServiceImpl(AnnouncementMapper announcementMapper) {
        this.announcementMapper = announcementMapper;
    }

    @Override
    // 1. 모든 공지사항 조회
    public List<AnnouncementDTO> findAllAnnouncements(){
        return announcementMapper.selectAll();
    }



    // 2. id로 공지사항 조회
    public AnnouncementDTO findAnnouncementById(Long announcementId){
        return announcementMapper.selectById(announcementId);
    }

    // 3. 키워드로 공지사항 조회
    public List<AnnouncementDTO> findAnnouncementByKeyword(String keyword){
        return announcementMapper.selectByKeyword(keyword);
    }

    // 4. 작성자id로 공지사항 조회
    public List<AnnouncementDTO> findAnnouncementByMemberId(Long memberId){
        return announcementMapper.selectByMemberId(memberId);
    }
}
