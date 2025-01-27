package beyond.momentours.announcement.command.application.controller;


import beyond.momentours.announcement.command.application.service.AnnouncementService;
import beyond.momentours.announcement.command.domain.aggregate.dto.request.CreateAnnouncementRequestDTO;
import beyond.momentours.announcement.command.domain.aggregate.dto.request.UpdateAnnouncementRequestDTO;
import beyond.momentours.announcement.command.domain.aggregate.dto.response.AnnouncementResponseDTO;
import beyond.momentours.common.ResponseDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import org.springframework.web.bind.annotation.*;

@RestController("commandAnnouncementController")
@RequestMapping("api/announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    // 1. 공지사항 등록
    @PostMapping("/create")
    public ResponseDTO<AnnouncementResponseDTO> createAnnouncement(
            @RequestBody CreateAnnouncementRequestDTO requestDTO,
            CustomUserDetails user // 로그인한 사용자의 정보 + 요청 DTO 정보
    ) {
        Long memberId = user.getMember().getMemberId(); // 로그인한 사용자 정보 받아오기
        AnnouncementResponseDTO responseDTO = announcementService.createAnnouncement(requestDTO, memberId);
        return ResponseDTO.ok(responseDTO);
    }

    // 2. 공지사항 수정
    @PutMapping("/my/{announcementId}/modify")
    public ResponseDTO<AnnouncementResponseDTO> modifyAnnouncement(
            @PathVariable Long announcementId,
            @RequestBody UpdateAnnouncementRequestDTO requestDTO,
            CustomUserDetails user  // 요청 DTO 정보 + 로그인 회원 정보
    ){
        Long memberId = user.getMember().getMemberId();
        AnnouncementResponseDTO responseDTO = announcementService.updateAnnouncement(announcementId, requestDTO, memberId);
        return ResponseDTO.ok(responseDTO);
    }

    // 3. 공지사항 삭제
    @DeleteMapping("/my/{announcementId}/delete")
    public void deleteAnnouncement(
            @PathVariable Long announcementId,
            @RequestBody CustomUserDetails user
    ) {
        Long memberId = user.getMember().getMemberId();
        announcementService.deleteAnnouncement(announcementId, memberId);
    }

}
