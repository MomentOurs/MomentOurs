package beyond.momentours.randomquestionanswer.command.application.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.randomquestionanswer.command.application.dto.RQAnswerDTO;
import beyond.momentours.randomquestionanswer.command.application.mapper.RQAnswerConverter;
import beyond.momentours.randomquestionanswer.command.application.service.RQAnswerService;
import beyond.momentours.randomquestionanswer.command.domain.vo.request.RequestRQAnswerCreatedVO;
import beyond.momentours.randomquestionanswer.command.domain.vo.request.RequestRQAnswerUpdatedVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/randomquestionanswer")
public class RQAnswerController {

    private final RQAnswerService rqAnswerService;
    private final RQAnswerConverter rqAnswerConverter;

    @Autowired
    public RQAnswerController(RQAnswerService rqAnswerService, RQAnswerConverter rqAnswerConverter) {
        this.rqAnswerService = rqAnswerService;
        this.rqAnswerConverter = rqAnswerConverter;
    }

    @PostMapping("")
    public ResponseDTO<?> createRQAnswer(@RequestBody RequestRQAnswerCreatedVO requestRQAnswerCreatedVO,
                                         @AuthenticationPrincipal CustomUserDetails user){
        RQAnswerDTO rqAnswerDTO = RQAnswerDTO.builder()
                .quesAnsContent(requestRQAnswerCreatedVO.getQuesAnsContent())
                .userQuesId(requestRQAnswerCreatedVO.getUserQuesId())
                .build();
        rqAnswerService.createRQAnswer(rqAnswerDTO, user);
        return ResponseDTO.ok("답변이 성공적으로 작성되었습니다.");
    }

    @PatchMapping("")
    public ResponseDTO<?> updateRQAnswer(@RequestBody RequestRQAnswerUpdatedVO requestRQAnswerUpdatedVO,
                                         @AuthenticationPrincipal CustomUserDetails user) {
        RQAnswerDTO rqAnswerDTO = rqAnswerConverter.updateVoToDTO(requestRQAnswerUpdatedVO);
        rqAnswerService.updateRQAnswer(rqAnswerDTO, user);
        return ResponseDTO.ok("답변이 성공적으로 수정되었습니다.");
    }
}
