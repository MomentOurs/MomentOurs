package beyond.momentours.randomquestionanswer.query.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.randomquestion.query.dto.UserRandomQuestionDTO;
import beyond.momentours.randomquestionanswer.query.dto.RQAnswerDTO;
import beyond.momentours.randomquestionanswer.query.repository.RQAnswerMapper;
import beyond.momentours.randomquestionanswer.query.vo.response.ResponseRQAnswerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("queryRQAnswerServiceImpl")
public class RQAnswerQueryServiceImpl implements RQAnswerQueryService {

    private final RQAnswerMapper rqAnswerMapper;

    @Autowired
    public RQAnswerQueryServiceImpl(RQAnswerMapper rqAnswerMapper) {
        this.rqAnswerMapper = rqAnswerMapper;
    }

    @Override
    public ResponseRQAnswerVO getRQAnswer(Long userQuesId, CustomUserDetails user) {

        Long memberId = user.getMemberId();

        List<RQAnswerDTO> rqAnswerList = rqAnswerMapper.findByQuesAnswerId(userQuesId);

        ResponseRQAnswerVO response = new ResponseRQAnswerVO();

        if (rqAnswerList.isEmpty()) {
            response.setMessage("이곳을 눌러서 답변을 입력해 주세요.");
        } else {
            for (RQAnswerDTO rqAnswerDTO : rqAnswerList) {
                if (rqAnswerDTO.getMemberId().equals(memberId)) {
                    response.setMyAnswer(rqAnswerDTO.getQuesAnsContent());
                } else {
                    response.setOtherAnswer(rqAnswerDTO.getQuesAnsContent());
                }
            }
        }

        if (response.getMessage() != null && response.getOtherAnswer() == null) {
            response.setMessage("상대방이 아직 답변하지 않았습니다.");
        } else if (response.getMyAnswer() == null && response.getOtherAnswer() != null) {
            response.setMessage("상대방의 답변을 보려면 답변을 작성하세요!");
            response.setOtherAnswer("BLURRED");
        } else {
            response.setMessage("두 사람의 답변을 모두 확인할 수 있습니다.");
        }

        return response;
    }
}
