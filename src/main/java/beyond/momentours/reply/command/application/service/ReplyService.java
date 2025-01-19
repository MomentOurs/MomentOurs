package beyond.momentours.reply.command.application.service;

import beyond.momentours.reply.command.application.dto.ReplyDTO;

public interface ReplyService {
    ReplyDTO createReply(ReplyDTO replyDTO);

    ReplyDTO updateReply(ReplyDTO replyDTO);

    ReplyDTO deleteReply(Long replyId);
}