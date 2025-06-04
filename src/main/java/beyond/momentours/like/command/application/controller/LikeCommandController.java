package beyond.momentours.like.command.application.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.like.command.application.service.LikeCommandService;
import beyond.momentours.like.command.domain.aggregate.LikeType;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeCommandController {

    private final LikeCommandService likeCommandService;

    @PostMapping
    public ResponseDTO<?> like(@RequestParam LikeType type, @RequestParam Long targetId, @AuthenticationPrincipal CustomUserDetails user) {
        likeCommandService.like(user.getMember().getMemberId(), type, targetId);
        return ResponseDTO.ok("좋아요 완료");
    }

    @DeleteMapping
    public ResponseDTO<?> unlike(@RequestParam LikeType type, @RequestParam Long targetId, @AuthenticationPrincipal CustomUserDetails user) {
        likeCommandService.unlike(user.getMember().getMemberId(), type, targetId);
        return ResponseDTO.ok("좋아요 취소 완료");
    }

    @GetMapping("/count")
    public ResponseDTO<Long> getLikeCount(@RequestParam LikeType type, @RequestParam Long targetId) {
        return ResponseDTO.ok(likeCommandService.getLikeCount(type, targetId));
    }
}
