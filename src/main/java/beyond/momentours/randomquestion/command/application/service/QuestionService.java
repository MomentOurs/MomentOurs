package beyond.momentours.randomquestion.command.application.service;

import beyond.momentours.member.command.application.dto.CustomUserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface QuestionService {

    void createNewQuestion(CustomUserDetails user) throws InterruptedException, ExecutionException, TimeoutException;

    void checkAndAssignToAllCouples() throws ExecutionException, InterruptedException, TimeoutException;

    @Transactional
    void assignNewQuestionToCouple(Long coupleId) throws InterruptedException, ExecutionException, TimeoutException;
}
