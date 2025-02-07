package beyond.momentours.announcement.query.service;

import beyond.momentours.announcement.query.dto.AnnouncementDTO;
import beyond.momentours.announcement.query.repository.AnnouncementMapper;
import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
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
        try{
            List<AnnouncementDTO> announcements = announcementMapper.selectAll();
            if(announcements == null || announcements.isEmpty()){
                throw new EntityNotFoundException("공지사항이 존재하지 않음");
            }
            return announcements;
        } catch (EntityNotFoundException e){
            throw new CommonException(ErrorCode.NOT_FOUND_NOTICE);
        }catch (Exception e){
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }


    // 2. id로 공지사항 조회
    public AnnouncementDTO findAnnouncementById(Long announcementId){
        try{
            AnnouncementDTO announcement = announcementMapper.selectById(announcementId);
            if(announcement == null){
                throw new EntityNotFoundException("공지사항이 존재하지 않음");
            }
            return announcement;
        } catch (EntityNotFoundException e){
            throw new CommonException(ErrorCode.NOT_FOUND_NOTICE);
        }catch (Exception e){
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    // 3. 키워드로 공지사항 조회
    public List<AnnouncementDTO> findAnnouncementByKeyword(String keyword){
        try{
            List<AnnouncementDTO> announcements = announcementMapper.selectByKeyword(keyword);
            if(announcements == null || announcements.isEmpty()){
                throw new EntityNotFoundException("공지사항이 존재하지 않음");
            }
            return announcements;
        } catch (EntityNotFoundException e){
            throw new CommonException(ErrorCode.NOT_FOUND_NOTICE);
        }catch (Exception e){
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 4. 작성자id로 공지사항 조회
    public List<AnnouncementDTO> findAnnouncementByMemberId(Long memberId){
        try{
            List<AnnouncementDTO> announcements = announcementMapper.selectByMemberId(memberId);
            if(announcements == null || announcements.isEmpty()){
                throw new EntityNotFoundException("공지사항이 존재하지 않음");
            }
            return announcements;
        } catch (EntityNotFoundException e){
            throw new CommonException(ErrorCode.NOT_FOUND_NOTICE);
        }catch (Exception e){
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
