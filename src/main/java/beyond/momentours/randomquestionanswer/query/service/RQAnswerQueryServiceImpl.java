package beyond.momentours.randomquestionanswer.query.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.randomquestionanswer.query.dto.RQAnswerDTO;
import beyond.momentours.randomquestionanswer.query.repository.RQAnswerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("queryRQAnswerServiceImpl")
public class RQAnswerQueryServiceImpl implements RQAnswerQueryService {

    private final RQAnswerMapper rqAnswerMapper;

    @Autowired
    public RQAnswerQueryServiceImpl(RQAnswerMapper rqAnswerMapper) {
        this.rqAnswerMapper = rqAnswerMapper;
    }

    @Override
    public RQAnswerDTO findByQuesAnswerIdAndMemberId(Long quesAnswerId, Long memberId) {
        RQAnswerDTO rqAnswer = rqAnswerMapper.findByQuesAnswerIdAndMemberId(quesAnswerId, memberId);
        if (rqAnswer == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_QUES_ANSWER);
        }
        return rqAnswer;
    }
}
