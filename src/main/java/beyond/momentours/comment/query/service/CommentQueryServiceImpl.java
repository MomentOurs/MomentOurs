package beyond.momentours.comment.query.service;

import beyond.momentours.comment.command.application.dto.CommentDTO;
import beyond.momentours.comment.command.application.mapper.CommentConverter;
import beyond.momentours.comment.command.domain.aggregate.entity.Comment;
import beyond.momentours.comment.query.repository.CommentMapper;
import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.moment.command.domain.aggregate.repository.MomentRepository;
import beyond.momentours.randomquestion.query.dto.UserRandomQuestionDTO;
import beyond.momentours.randomquestion.query.repository.RandomQuestionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentQueryServiceImpl implements CommentQueryService {

    private final CommentConverter commentConverter;
    private final CommentMapper commentDAO;
    private final RandomQuestionMapper randomQuestionMapper;
    private final MomentRepository momentRepository;

    @Override
    public List<CommentDTO> getCommentsByMomentId(Long momentId) {
        List<Comment> comments = commentDAO.findCommentsByMomentId(momentId);
        log.info("조회된 추억 댓글 목록: {}", comments);

        return comments.stream()
                .map(commentConverter::fromEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> getCommentsByQuestionId(Long userQuesId) {
        String ansStatus = randomQuestionMapper.findAnsStatusByUserQuesId(userQuesId);
        log.info("랜덤 질문의 ansStatus: {}", ansStatus);

        if (!"ALL".equals(ansStatus)) throw new CommonException(ErrorCode.INVALID_RANDOM_QUESTION_STATUS);

        List<Comment> comments = commentDAO.findCommentsByQuestionId(userQuesId);
        log.info("조회된 질문 댓글 목록: {}", comments);

        return comments.stream()
                .map(commentConverter::fromEntityToDTO)
                .collect(Collectors.toList());
    }

//    private void validateCommentTypeStatus(CommentDTO commentDTO) {
//        switch (commentDTO.getCommentType()) {
//            case MOMENT:
//                momentRepository.findById(commentDTO.getTargetId()).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_MOMENT));
//                break;
//
//            case QUESTION:
//                UserRandomQuestionDTO randomQuestion = randomQuestionMapper.findByUserQuesId(commentDTO.getTargetId());
//                if (randomQuestion == null) throw new CommonException(ErrorCode.NOT_FOUND_RANDOM_QUESTION);
//                if (!"ALL".equals(randomQuestion.getAnsStatus())) {
//                    throw new CommonException(ErrorCode.INVALID_RANDOM_QUESTION_STATUS);
//                }
//                break;
//
//            default:
//                throw new CommonException(ErrorCode.INVALID_COMMENT_TYPE);
//        }
//    }
}
