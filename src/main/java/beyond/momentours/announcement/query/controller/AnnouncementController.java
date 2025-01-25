package beyond.momentours.announcement.query.controller;


import beyond.momentours.announcement.query.dto.AnnouncementDTO;
import beyond.momentours.announcement.query.service.AnnouncementService;
import beyond.momentours.common.ResponseDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("queryAnnouncementController")
@RequestMapping("api/announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementController (AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    // 1. 모든 공지사항 조회
    @GetMapping("/all")
    public ResponseDTO<List<AnnouncementDTO>> getAllAnnouncements() {
        List<AnnouncementDTO> responsee = announcementService.findAllAnnouncements();
        return ResponseDTO.ok(responsee);
    }

    // 2. id로 공지사항 조회
    @GetMapping("/id")
    public ResponseDTO<AnnouncementDTO> getAnnouncementById(@PathVariable Long id){
        AnnouncementDTO response = announcementService.findAnnouncementById(id);
        return ResponseDTO.ok(response);
    }

    // 3. 키워드로 공지사항 조회
    @GetMapping("/keyword")
    public ResponseDTO<List<AnnouncementDTO>> getAnnouncementByKeyword(@RequestParam String keyword){
        List<AnnouncementDTO> response = announcementService.findAnnouncementByKeyword(keyword);
        return ResponseDTO.ok(response);
    }

    // 4. 작성자 id로 공지사항 조회
    @GetMapping("/memberId")
    public ResponseDTO<List<AnnouncementDTO>> getAnnouncementByMemberId(@RequestParam Long memberId){
        List<AnnouncementDTO> response = announcementService.findAnnouncementByMemberId(memberId);
        return ResponseDTO.ok(response);
    }


    // 5. 로그인한 사용자의 공지사항 조회
    @GetMapping("/my")
    public ResponseDTO<List<AnnouncementDTO>> getMyAnnouncements(CustomUserDetails user) {
        // 로그인된 사용자의 memberId
        Long memberId = user.getMember().getMemberId();

        // 사용자가 작성한 공지사항 조회
        List<AnnouncementDTO> response = announcementService.findAnnouncementByMemberId(memberId);
        return ResponseDTO.ok(response);
    }

}
