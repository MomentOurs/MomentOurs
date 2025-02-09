package beyond.momentours.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

//    @Bean
//    public WebClient webClient() {
//        return WebClient.builder()
//                .baseUrl("https://api.openai.com")
//                .defaultHeader("Content-Type", "application/json")
//                .build();
//    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
