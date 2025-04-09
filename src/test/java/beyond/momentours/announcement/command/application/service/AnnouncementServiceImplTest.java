package beyond.momentours.announcement.command.application.service;

import beyond.momentours.announcement.command.domain.aggregate.dto.request.CreateAnnouncementRequestDTO;
import beyond.momentours.announcement.command.domain.aggregate.dto.request.UpdateAnnouncementRequestDTO;
import beyond.momentours.announcement.command.domain.aggregate.dto.response.AnnouncementResponseDTO;
import beyond.momentours.announcement.command.domain.aggregate.entity.Announcement;
import beyond.momentours.announcement.command.domain.repository.AnnouncementRepository;
import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class AnnouncementServiceImplTest {

    @Mock
    private AnnouncementRepository announcementRepository;

    @InjectMocks
    private AnnouncementServiceImpl announcementService;

    private CreateAnnouncementRequestDTO createRequestDTO;
    private UpdateAnnouncementRequestDTO updateRequestDTO;
    private Long memberId;
    private Long announcementId;
    private Announcement announcement;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        memberId = 1L;
        announcementId = 1L;

        createRequestDTO = new CreateAnnouncementRequestDTO("Test Title", "Test Content");
        updateRequestDTO = new UpdateAnnouncementRequestDTO("Updated Title", "Updated Content");

        // 빌더를 이용해 Announcement 객체 생성
        announcement = Announcement.builder()
                .announcementId(announcementId)
                .announcementTitle("Test Title")
                .announcementContent("Test Content")
                .memberId(memberId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testCreateAnnouncement() {
        when(announcementRepository.save(any(Announcement.class))).thenReturn(announcement);

        AnnouncementResponseDTO responseDTO = announcementService.createAnnouncement(createRequestDTO, memberId);

        assertNotNull(responseDTO);
        assertEquals("Test Title", responseDTO.getAnnouncementTitle());
        verify(announcementRepository, times(1)).save(any(Announcement.class));
    }

    @Test
    void testUpdateAnnouncement() {
        when(announcementRepository.findById(announcementId)).thenReturn(Optional.of(announcement));

        // 빌더 패턴 사용: toBuilder를 이용해 객체 수정
        Announcement updatedAnnouncement = announcement.toBuilder()
                .announcementTitle(updateRequestDTO.getAnnouncementTitle())
                .announcementContent(updateRequestDTO.getAnnouncementContent())
                .updatedAt(LocalDateTime.now())
                .build();

        when(announcementRepository.save(any(Announcement.class))).thenReturn(announcement);

        AnnouncementResponseDTO responseDTO = announcementService.updateAnnouncement(announcementId, updateRequestDTO, memberId);

        assertNotNull(responseDTO);
        assertEquals("Updated Title", responseDTO.getAnnouncementTitle());
        verify(announcementRepository, times(1)).save(any(Announcement.class));
    }

    @Test
    void testDeleteAnnouncement() {
        when(announcementRepository.findById(announcementId)).thenReturn(Optional.of(announcement));

        announcementService.deleteAnnouncement(announcementId, memberId);

        verify(announcementRepository, times(1)).deleteById(announcementId);
    }

    @Test
    void testCreateAnnouncement_Exception() {
        when(announcementRepository.save(any(Announcement.class))).thenThrow(new RuntimeException("Error"));

        Exception exception = assertThrows(CommonException.class, () -> {
            announcementService.createAnnouncement(createRequestDTO, memberId);
        });

        Assertions.assertEquals("서버 내부 오류입니다", exception.getMessage());

    }

    @Test
    void testUpdateAnnouncement_NoPermission() {
        when(announcementRepository.findById(announcementId)).thenReturn(Optional.of(announcement));

        Exception exception = assertThrows(CommonException.class, () -> {
            announcementService.updateAnnouncement(announcementId, updateRequestDTO, 2L); // 다른 회원
        });

        Assertions.assertEquals("요청한 리소스에 대한 권한이 없습니다.", exception.getMessage());

    }

    @Test
    void testDeleteAnnouncement_NoPermission() {
        when(announcementRepository.findById(announcementId)).thenReturn(Optional.of(announcement));

        Exception exception = assertThrows(CommonException.class, () -> {
            announcementService.deleteAnnouncement(announcementId, 2L); // 다른 회원
        });

        Assertions.assertEquals("요청한 리소스에 대한 권한이 없습니다.", exception.getMessage());

    }

}