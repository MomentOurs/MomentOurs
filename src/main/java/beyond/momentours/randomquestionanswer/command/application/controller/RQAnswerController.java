package beyond.momentours.randomquestionanswer.command.application.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.randomquestionanswer.command.application.dto.RQAnswerDTO;
import beyond.momentours.randomquestionanswer.command.application.service.RQAnswerService;
import beyond.momentours.randomquestionanswer.command.domain.vo.request.RequestRQAnswerCreatedVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/randomquestionanswer/")
@RequiredArgsConstructor
public class RQAnswerController {

    private final RQAnswerService rqAnswerService;

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
}
