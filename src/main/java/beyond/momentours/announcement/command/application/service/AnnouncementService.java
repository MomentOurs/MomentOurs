package beyond.momentours.announcement.command.application.service;

import beyond.momentours.announcement.command.domain.aggregate.dto.request.CreateAnnouncementRequestDTO;
import beyond.momentours.announcement.command.domain.aggregate.dto.request.UpdateAnnouncementRequestDTO;
import beyond.momentours.announcement.command.domain.aggregate.dto.response.AnnouncementResponseDTO;
import beyond.momentours.announcement.command.domain.aggregate.entity.Announcement;
import beyond.momentours.member.command.application.dto.CustomUserDetails;

public interface AnnouncementService {

    // 1. 공지사항 등록 서비스
    AnnouncementResponseDTO createAnnouncement(CreateAnnouncementRequestDTO requestDTO, Long memberId);


    // 2. 공지사항 수정 서비스
    AnnouncementResponseDTO updateAnnouncement(Long announcementId, UpdateAnnouncementRequestDTO requestDTO,Long memberId);


    // 3. 공지사항 삭제 서비스
   void deleteAnnouncement(Long announcementId, Long memberId);
}
