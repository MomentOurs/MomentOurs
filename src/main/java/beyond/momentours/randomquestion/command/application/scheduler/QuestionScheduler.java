package beyond.momentours.randomquestion.command.application.scheduler;

import beyond.momentours.randomquestion.command.application.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service
public class QuestionScheduler {

    private final QuestionService questionService;

    @Autowired
    public QuestionScheduler(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void assignDailyQuestions() throws ExecutionException, InterruptedException, TimeoutException {
        questionService.checkAndAssignToAllCouples();
    }
}
