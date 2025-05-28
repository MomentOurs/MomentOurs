package beyond.momentours.comment.command.application.service;

import beyond.momentours.comment.command.application.dto.CommentDTO;
import beyond.momentours.comment.command.application.mapper.CommentConverter;
import beyond.momentours.comment.command.domain.aggregate.entity.Comment;
import beyond.momentours.comment.command.domain.repository.CommentRepository;
import beyond.momentours.comment.query.repository.CommentMapper;
import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.moment.command.domain.aggregate.repository.MomentRepository;
import beyond.momentours.randomquestion.query.dto.UserRandomQuestionDTO;
import beyond.momentours.randomquestion.query.repository.RandomQuestionMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentCommandServiceImpl implements CommentCommandService {

    private final CommentRepository commentRepository;
    private final CommentConverter commentConverter;
    private final CommentMapper commentDAO;
    private final RandomQuestionMapper randomQuestionMapper;
    private final MomentRepository momentRepository;

    @Transactional
    @Override
    public CommentDTO createComment(CommentDTO commentDTO, CustomUserDetails user) {
        CommentDTO commentDTOInfo = setCommentTypeFields(commentDTO);

        Long memberId = user.getMemberId();
        commentDTO.setMemberId(memberId);

        validateCommentTypeStatus(commentDTO); // 해당 댓글 출처의 상태 검증 로직

        Comment comment = commentConverter.fromDTOToEntity(commentDTOInfo);
        log.info("저장할 댓글 데이터: {}", comment);

        comment.create(comment);
        Comment savedComment = commentRepository.save(comment);

        return commentConverter.fromEntityToDTO(savedComment);
    }

    @Transactional
    @Override
    public CommentDTO updateComment(CommentDTO commentDTO, CustomUserDetails user) {
        Long memberId = user.getMemberId();
        commentDTO.setMemberId(memberId);

        Comment existingComment = commentRepository.findById(commentDTO.getCommentId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_COMMENT));
        log.info("기존 Comment 데이터: {}", existingComment);

        if (!existingComment.getMemberId().equals(memberId)) {
            log.error("수정 권한 없음: 요청한 사용자 ID: {}, 댓글 작성자 ID: {}", memberId, existingComment.getMemberId());
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        validateCommentTypeStatus(commentDTO);

        updateCommentContent(commentDTO, existingComment);
        log.info("수정 후 Comment 데이터: {}", existingComment);

        commentRepository.save(existingComment);

        return commentConverter.fromEntityToDTO(existingComment);
    }

    @Transactional
    @Override
    public CommentDTO deleteComment(Long commentId, CustomUserDetails user) {
        Comment existingComment = commentRepository.findById(commentId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_COMMENT));
        log.info("삭제 요청된 Comment 데이터: {}", existingComment);

        Long memberId = user.getMemberId();
        log.info("삭제 요청된 Comment 데이터: {}", memberId);

        if (!existingComment.getMemberId().equals(memberId)) {
            log.error("삭제 권한 없음: 요청한 사용자 ID: {}, 댓글 작성자 ID: {}", memberId, existingComment.getMemberId());
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        CommentDTO commentDTO = commentConverter.fromEntityToDTO(existingComment);
        validateCommentTypeStatus(commentDTO);

        existingComment.updateStatus(false);
        log.info("상태 변경 후 Comment : {}", existingComment);

        commentRepository.save(existingComment);

        return commentConverter.fromEntityToDTO(existingComment);
    }

    private void updateCommentContent(CommentDTO commentDTO, Comment existingComment) {
        if (commentDTO.getCommentContent() != null) {
            existingComment.updateContent(commentDTO.getCommentContent());
        }
    }

    private CommentDTO setCommentTypeFields(CommentDTO commentDTO) {
        switch (commentDTO.getCommentType()) {
            case MOMENT:
                log.info("MOMENT 타입의 댓글 처리");
                commentDTO.setTargetId(commentDTO.getTargetId());
                break;

            case QUESTION:
                log.info("QUESTION 타입의 댓글 처리");
                commentDTO.setTargetId(commentDTO.getTargetId());
                break;

            default:
                throw new CommonException(ErrorCode.INVALID_COMMENT_TYPE);
        }

        return commentDTO;
    }

    private void validateCommentTypeStatus(CommentDTO commentDTO) {
        switch (commentDTO.getCommentType()) {
            case MOMENT:
                momentRepository.findById(commentDTO.getTargetId()).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_MOMENT));
                break;

            case QUESTION:
                UserRandomQuestionDTO randomQuestion = randomQuestionMapper.findByUserQuesId(commentDTO.getTargetId());
                if (randomQuestion == null) throw new CommonException(ErrorCode.NOT_FOUND_RANDOM_QUESTION);
                if (!"ALL".equals(randomQuestion.getAnsStatus())) {
                    throw new CommonException(ErrorCode.INVALID_RANDOM_QUESTION_STATUS);
                }
                break;

            default:
                throw new CommonException(ErrorCode.INVALID_COMMENT_TYPE);
        }
    }
}
