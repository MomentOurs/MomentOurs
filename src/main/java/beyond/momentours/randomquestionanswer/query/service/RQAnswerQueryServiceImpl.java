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

        // 특정 질문에 대한 모든 답변 가져오기
        List<RQAnswerDTO> rqAnswerList = rqAnswerMapper.findByQuesAnswerId(userQuesId);

        ResponseRQAnswerVO response = new ResponseRQAnswerVO();
        String myAnswer = null;
        String otherAnswer = null;

        // 응답 리스트를 순회하면서 내 답변과 상대방 답변 분류
        for (RQAnswerDTO rqAnswerDTO : rqAnswerList) {
            if (rqAnswerDTO.getMemberId().equals(memberId)) {
                myAnswer = rqAnswerDTO.getQuesAnsContent();
            } else {
                otherAnswer = rqAnswerDTO.getQuesAnsContent();
            }
        }

        // 조건에 따른 메시지 설정
        if (myAnswer == null && otherAnswer == null) {
            // 1. 나와 상대방 모두 답변 안 했을 때
            response.setMyAnswer("");
            response.setOtherAnswer("상대방이 아직 답변하지 않았어요.");
        } else if (myAnswer != null && otherAnswer == null) {
            // 2. 나는 답변했고, 상대방은 안 했을 때
            response.setMyAnswer(myAnswer);
            response.setOtherAnswer("상대방이 아직 답변하지 않았어요.");
        } else if (myAnswer == null && otherAnswer != null) {
            // 3. 나는 답변 안 했고, 상대방은 답변했을 때
            response.setMyAnswer("");
            response.setOtherAnswer("상대방의 답변이 궁금한가요? 그럼 오늘 질문에 답변해 주세요!");
        } else {
            // 4. 나와 상대방 둘 다 답변했을 때
            response.setMyAnswer(myAnswer);
            response.setOtherAnswer(otherAnswer);
        }

        return response;
    }
}
