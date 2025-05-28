package beyond.momentours.like.query.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.like.command.domain.aggregate.LikeType;
import beyond.momentours.like.query.service.LikeQueryService;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeQueryController {

    private final LikeQueryService likeQueryService;

    @GetMapping("/exists")
    public ResponseDTO<Boolean> hasUserLiked(@RequestParam LikeType type, @RequestParam Long targetId, @AuthenticationPrincipal CustomUserDetails user) {
        boolean liked = likeQueryService.hasUserLiked(user.getMember().getMemberId(), type, targetId);
        return ResponseDTO.ok(liked);
    }

}
