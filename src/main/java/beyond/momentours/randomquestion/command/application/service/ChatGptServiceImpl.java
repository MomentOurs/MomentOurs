package beyond.momentours.randomquestion.command.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class ChatGptServiceImpl implements ChatGptService {

    private final WebClient webClient;

    public ChatGptServiceImpl(WebClient.Builder webClientBuilder,
                              @Value("${openai.api-url}") String apiUrl) {
        this.webClient = webClientBuilder.baseUrl(apiUrl).build();
    }

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.question-count}")
    private int questionCount;

    @Override
    public List<String> fetchQuestionsFromChatGPT() throws InterruptedException, ExecutionException, TimeoutException {
        String prompt = "커플이 함께 답할 수 있는 랜덤 질문 " + questionCount + "개를 생성해줘. " +
                "질문은 간단하고 대화형식으로 만들어줘.";

        // API 키 로그 출력 (디버깅용)
        log.info("사용하는 OpenAI API 키: {}", apiKey);

        // 재시도 로직
        int retries = 3;
        while (retries > 0) {
            try {
                // OpenAI API 요청
                Mono<Map<String, Object>> responseMono = webClient.post()
                        .uri("/v1/chat/completions")
                        .header("Authorization", "Bearer " + apiKey)
                        .header("Content-Type", "application/json")
                        .bodyValue(Map.of(
                                "model", model,
                                "messages", List.of(Map.of("role", "user", "content", prompt)),
                                "temperature", 0.7
                        ))
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<>() {});

                // 응답 처리
                Map<String, Object> response = responseMono.block();
                if (response == null || !response.containsKey("choices")) {
                    throw new RuntimeException("ChatGPT 응답 오류: choices 필드 없음");
                }
                return extractQuestions(response);

            } catch (WebClientResponseException.TooManyRequests e) {
                log.warn("429 Too Many Requests - 재시도...");
                retries--;
                try {
                    Thread.sleep(5000); // 2초 후 재시도
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread interrupted during retry sleep", ex);
                }
            } catch (Exception e) {
                log.error("API 호출 중 오류 발생: {}", e.getMessage());
                throw new RuntimeException("API 호출 실패", e);
            }
        }

        throw new RuntimeException("최대 재시도 횟수를 초과했습니다.");
    }

    private List<String> extractQuestions(Map<String, Object> response) {
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        if (choices == null || choices.isEmpty()) {
            throw new RuntimeException("ChatGPT 응답 오류: choices 배열이 비어 있음");
        }

        Map<String, Object> firstChoice = choices.get(0);
        Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
        if (message == null || !message.containsKey("content")) {
            throw new RuntimeException("ChatGPT 응답 오류: message.content 없음");
        }

        String content = (String) message.get("content");
        return List.of(content.split("\n"));
    }
}
