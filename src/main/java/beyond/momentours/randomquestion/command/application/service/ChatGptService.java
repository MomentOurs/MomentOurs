package beyond.momentours.randomquestion.command.application.service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface ChatGptService {
    // ChatGPT API를 호출하여 질문 리스트를 받아옴
    List<String> fetchQuestionsFromChatGPT() throws InterruptedException, ExecutionException, TimeoutException;
}
