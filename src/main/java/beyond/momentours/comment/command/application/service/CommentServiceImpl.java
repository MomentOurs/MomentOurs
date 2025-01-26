package beyond.momentours.comment.command.application.service;

import beyond.momentours.comment.command.application.dto.CommentDTO;
import beyond.momentours.comment.command.application.mapper.CommentConverter;
import beyond.momentours.comment.command.domain.aggregate.entity.Comment;
import beyond.momentours.comment.command.domain.repository.CommentRepository;
import beyond.momentours.comment.query.repository.CommentMapper;
import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("commandCommentService")
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentConverter commentConverter;
    private final CommentMapper commentDAO;

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


    @Override
    public CommentDTO deleteComment(Long commentId, CustomUserDetails user) {
        Comment existingComment = commentRepository.findById(commentId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_COMMENT));
        log.info("삭제 요청된 Comment 데이터: {}", existingComment);

        Long memberId = user.getMemberId();

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
            case COUPLE_LOG:
                log.info("COUPLE_LOG 타입의 댓글 처리");
                commentDTO.setCoupleLogId(commentDTO.getCoupleLogId());
                commentDTO.setQuesId(null);
                break;

            case QUESTION:
                log.info("QUESTION 타입의 댓글 처리");
                commentDTO.setQuesId(commentDTO.getQuesId());
                commentDTO.setCoupleLogId(null);
                break;

            default:
                throw new CommonException(ErrorCode.INVALID_COMMENT_TYPE);
        }

        return commentDTO;
    }

    private void validateCommentTypeStatus(CommentDTO commentDTO) {
        switch (commentDTO.getCommentType()) {
//            case COUPLE_LOG:
//                CoupleLog coupleLog = coupleLogRepository.findById(commentDTO.getCoupleLogId())
//                        .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_COUPLE_LOG));
//                break;
//
//            case QUESTION:
//                RandomQuestion randomQuestion = randomQuestionRepository.findById(commentDTO.getQuesId())
//                        .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RANDOM_QUESTION));
//                if (!"All".equals(randomQuestion.getAnsStatus())) {
//                    throw new CommonException(ErrorCode.INVALID_RANDOM_QUESTION_STATUS);
//                }
//                break;

            default:
                throw new CommonException(ErrorCode.INVALID_COMMENT_TYPE);
        }
    }
}
