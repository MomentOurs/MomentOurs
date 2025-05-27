package beyond.momentours.moment.command.application.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.moment.command.application.dto.MomentDTO;
import beyond.momentours.moment.command.application.service.MomentCommandService;
import beyond.momentours.moment.command.domain.aggregate.vo.request.RequestCreateMomentVO;
import beyond.momentours.moment.command.domain.aggregate.vo.request.RequestUpdateMomentVO;
import beyond.momentours.moment.common.converter.MomentConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/moment")
@RequiredArgsConstructor
public class MomentCommandController {

    private final MomentCommandService momentCommandService;
    private final MomentConverter momentConverter;

    @PostMapping
    public ResponseDTO<?> createMoment(@RequestBody RequestCreateMomentVO createMomentVO, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("등록 요청 데이터 : {}", createMomentVO);
        Long memberId = user.getMember().getMemberId();
        MomentDTO momentDTO = momentConverter.fromCreateVOToDTO(createMomentVO);
        MomentDTO responseMomentDTO = momentCommandService.createMoment(momentDTO, memberId);
        return ResponseDTO.ok(momentConverter.fromDTOToCreateVO(responseMomentDTO));
    }

    @PatchMapping
    public ResponseDTO<?> updateMoment(@RequestBody RequestUpdateMomentVO updateVO, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("수정 요청 데이터 : {}", updateVO);
        MomentDTO dto = momentConverter.fromUpdateVOToDTO(updateVO);
        MomentDTO responseMomentDTO = momentCommandService.updateMoment(dto, user);
        return ResponseDTO.ok(momentConverter.fromDTOToUpdateVO(responseMomentDTO));
    }

    @PatchMapping("/{momentId}/deactivate")
    public ResponseDTO<?> deleteMoment(@PathVariable Long momentId, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("추억 삭제 요청: momentId={}, userId={}", momentId, user.getMember().getMemberId());
        momentCommandService.deleteMoment(momentId, user);
        return ResponseDTO.ok("삭제되었습니다.");
    }

    @PatchMapping("/{momentId}/comment-status")
    public ResponseDTO<?> toggleCommentStatus(@PathVariable Long momentId, @RequestParam boolean enabled, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("댓글 허용 상태 변경 요청: momentId={}, enabled={}, userId={}", momentId, enabled, user.getMember().getMemberId());
        momentCommandService.toggleCommentStatus(momentId, enabled, user);
        return ResponseDTO.ok("댓글 허용 상태가 변경되었습니다.");
    }
}
