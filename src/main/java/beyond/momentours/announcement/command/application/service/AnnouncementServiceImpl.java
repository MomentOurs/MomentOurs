package beyond.momentours.announcement.command.application.service;

import beyond.momentours.announcement.command.domain.aggregate.dto.request.CreateAnnouncementRequestDTO;
import beyond.momentours.announcement.command.domain.aggregate.dto.request.UpdateAnnouncementRequestDTO;
import beyond.momentours.announcement.command.domain.aggregate.dto.response.AnnouncementResponseDTO;
import beyond.momentours.announcement.command.domain.aggregate.entity.Announcement;
import beyond.momentours.announcement.command.domain.repository.AnnouncementRepository;
import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private static final Logger log = LoggerFactory.getLogger(AnnouncementServiceImpl.class);

    @Autowired
    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    // 1. 공지사항 등록 서비스
    @Override
    @Transactional
    public AnnouncementResponseDTO createAnnouncement(CreateAnnouncementRequestDTO requestDTO, Long memberId){

        try {
            Announcement announcement = Announcement.builder()
                    .announcementTitle(requestDTO.getAnnouncementTitle())
                    .announcementContent(requestDTO.getAnnouncementContent())
                    .announcementStatus(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .memberId(memberId)
                    .build();

            // 엔터티 객체 저장하기
            Announcement createdAnnouncement = announcementRepository.save(announcement);

            // 엔티티를 ResponseDTO로 변환하여 반환
            return AnnouncementResponseDTO.fromEntity(createdAnnouncement);
        }
        catch (Exception e) {
            // 등록 실패 시
            log.error("공지 등록에 실패함");
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }


    // 2. 공지사항 수정 서비스
    // 설명. 자신이 등록한 공지사항이 아니면 수정 권한이 없음
    @Override
    @Transactional
    public AnnouncementResponseDTO updateAnnouncement(Long announcementId ,UpdateAnnouncementRequestDTO requestDTO, Long memberId){

        Announcement originalAnnouncement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_NOTICE));

        // 작성자와 로그인한 사용자가 동일한지 확인
        if (!originalAnnouncement.getMemberId().equals(memberId)) {
            throw new CommonException(ErrorCode. FORBIDDEN_ROLE);  // 권한 없음
        }

        try {
            // 권한이 있는 경우 수정
            // 기존 객체를 복사하면서 일부 속성 수정하기
            Announcement updatedAnnouncement = originalAnnouncement.toBuilder()
                    .announcementTitle(requestDTO.getAnnouncementTitle())
                    .announcementContent(requestDTO.getAnnouncementContent())
                    .updatedAt(LocalDateTime.now())
                    .build();

            announcementRepository.save(updatedAnnouncement);
            return AnnouncementResponseDTO.fromEntity(updatedAnnouncement);
        } catch (Exception e) {
            // 수정 실패
            log.error("공지 수정 실패함");
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }


    // 3. 공지사항 삭제 서비스
    // 설명. 자신이 등록한 공지사항이 아니면 삭제 권한이 없음
    @Override
    @Transactional
    public AnnouncementResponseDTO deleteAnnouncement(Long announcementId, Long memberId) {

        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_NOTICE));

        // 작성자와 로그인 한 사용자가 동일한지 확인
        if(!announcement.getMemberId().equals(memberId)) {
            throw new CommonException(ErrorCode. FORBIDDEN_ROLE);
        }
        try {
            // 권한이 있는 경우 삭제
            // announcementStatus = false로 변경
            Announcement updatedAnnouncement = announcement.toBuilder()
                    .announcementStatus(false)
                    .build();

            announcementRepository.save(updatedAnnouncement);
            return AnnouncementResponseDTO.fromEntity(updatedAnnouncement);
        }
        catch (Exception e) {
            // 삭제 실패
            log.error("공지 삭제에 실패함");
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
