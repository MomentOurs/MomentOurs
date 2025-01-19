package beyond.momentours.reply.command.application.service;

import beyond.momentours.reply.command.application.dto.ReplyDTO;
import beyond.momentours.reply.command.application.mapper.ReplyConvertor;
import beyond.momentours.reply.command.domain.aggregate.entity.Reply;
import beyond.momentours.reply.command.domain.repository.ReplyRepository;
import beyond.momentours.reply.query.repository.ReplyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("commandReplyService")
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final ReplyConvertor replyConvertor;
    private final ReplyMapper replyDAO;

    @Override
    public ReplyDTO createReply(ReplyDTO replyDTO) {
//        Long memberId = getLoggedInMemberId(); // 로그인한 사용자의 ID 가져오기
        Long memberId = 0L;
        replyDTO.setMemberId(memberId);

        Reply reply = replyConvertor.fromDTOToEntity(replyDTO);
        log.info("저장할 대댓글 데이터: {}", reply);

        reply.create(reply);
        Reply savedReply = replyRepository.save(reply);

        return replyConvertor.fromEntityToDTO(savedReply);
    }
}
