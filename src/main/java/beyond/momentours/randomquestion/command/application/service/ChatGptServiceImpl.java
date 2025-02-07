package beyond.momentours.randomquestion.command.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

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

    // ChatGPT API를 호출하여 질문 리스트를 받아옴
    @Override
    public List<String> fetchQuestionsFromChatGPT() {
        String prompt = "커플이 함께 답할 수 있는 랜덤 질문 " + questionCount + "개를 생성해줘. " +
                "질문은 간단하고 대화형식으로 만들어줘.";

        // OpenAI API 요청
        Mono<Map<String, Object>> responseMono = webClient.post()
                .uri("/v1/chat/completions") // baseUrl 사용
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
        Map<String, Object> response = responseMono.block(); // 동기 방식으로 변환
        return extractQuestions(response);
    }

    // OpenAI API 응답에서 질문을 추출하는 메서드
    private List<String> extractQuestions(Map<String, Object> response) {
        if (response == null || !response.containsKey("choices")) {
            throw new RuntimeException("ChatGPT 응답 오류");
        }

        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        String content = (String) ((Map<String, Object>) choices.get(0).get("message")).get("content");

        return List.of(content.split("\n"));
    }
}
