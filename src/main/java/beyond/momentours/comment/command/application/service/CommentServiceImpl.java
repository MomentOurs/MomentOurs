package beyond.momentours.comment.command.application.service;

import beyond.momentours.comment.command.application.dto.CommentDTO;
import beyond.momentours.comment.command.application.mapper.CommentConverter;
import beyond.momentours.comment.command.domain.aggregate.entity.Comment;
import beyond.momentours.comment.command.domain.repository.CommentRepository;
import beyond.momentours.comment.query.repository.CommentMapper;
import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
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
    public CommentDTO createComment(CommentDTO commentDTO) {
        CommentDTO commentDTOInfo = setCommentTypeFields(commentDTO);
//        Long memberId = getLoggedInMemberId(); // getLoggedInMemberId 는 로그인한 유저의 memberId를 토큰에서 가져온다는 가정으로 써둔 메서드
        Long memberId = 0L;
        commentDTO.setMemberId(memberId);

        Comment comment = commentConverter.fromDTOToEntity(commentDTOInfo);

        log.info("저장할 댓글 데이터: {}", comment);

        comment.create(comment);
        Comment savedComment = commentRepository.save(comment);

        return commentConverter.fromEntityToDTO(savedComment);
    }

    @Override
    public CommentDTO updateComment(CommentDTO commentDTO) {
//        Long memberId = getLoggedInMemberId(); // 로그인한 사용자의 ID 가져오기
        Long memberId = 0L;
        commentDTO.setMemberId(memberId);

        Comment existingComment = commentRepository.findById(commentDTO.getCommentId()).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_COMMENT));
        log.info("기존 Comment 데이터: {}", existingComment);

        if (!existingComment.getMemberId().equals(memberId)) {
            log.error("수정 권한 없음: 요청한 사용자 ID: {}, 댓글 작성자 ID: {}", memberId, existingComment.getMemberId());
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        updateCommentContent(commentDTO, existingComment);
        log.info("수정 후 Comment 데이터: {}", existingComment);

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
                commentDTO.setMomentId(null);
                break;

            case MOMENT:
                log.info("MOMENT 타입의 댓글 처리");
                commentDTO.setMomentId(commentDTO.getMomentId());
                commentDTO.setCoupleLogId(null);
                break;

            default:
                throw new CommonException(ErrorCode.INVALID_COMMENT_TYPE);
        }

        return commentDTO;
    }
}
