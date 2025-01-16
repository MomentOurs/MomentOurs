package beyond.momentours.comment.command.application.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("commandCommentController")
@RequestMapping("api/comment")
@Slf4j
@RequiredArgsConstructor
public class CommentController {
}
