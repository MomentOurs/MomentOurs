package beyond.momentours.randomquestion.command.application.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface QuestionService {

    // 사용하지 않은 질문이 3개 이하면 새로운 질문 생성 후 저장
    void createNewQuestion(Long coupleId) throws InterruptedException, ExecutionException, TimeoutException;
}
