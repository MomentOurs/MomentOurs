package beyond.momentours.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitMQConfig {

    @Bean // 성능 향상을 위해 연결을 재사용함
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("localhost");  // RabbitMQ 서버 호스트
        factory.setPort(5672);  // 기본 포트
        factory.setUsername("momentours");   // 기본 사용자명
        factory.setPassword("momentours");   // 기본 사용자 비밀번호

        // 메세지 전송 확인을 위한 설정(메세지가 브로커에 정상적으로 전달되었는지 확인할 수 있게끔 해줌)
        factory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        // 메세지가 Queue에 라우팅되지 못 할 경우 알림을 받을 수 있게끔 세팅
        factory.setPublisherReturns(true);

        return factory;
    }

    // rabbitTemplate 설정
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        // 메세지 변환기 설정
        rabbitTemplate.setMessageConverter(jackSonConverter());

        // 메세지가 라우팅되지 않을 때 처리
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback(returned -> {
            log.error("반환된 메세지 내용: {}", returned);
            retryMessage(rabbitTemplate, returned.getMessage(),
                    returned.getExchange(),
                    returned.getRoutingKey(),
                    3);
        });

        // 메세지 라우팅 성공 콜백
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) { // 잘 전달되지 않았다면
                log.error("컨펌되지 않은 메세지 내용: {}", cause);
                // correlationData에서 메세지 정보를 가져와 재전송
                if (correlationData != null) {
                    retryMessage(rabbitTemplate,
                            correlationData.getReturned().getMessage(),
                            "couple-log.exchange",
                            "couple-log.*.exchanges",
                            3);
                }
            }
        });
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jackSonConverter() {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())   // LocalDateTime 같은 시간 클래스를 Json으로 변환
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 날짜를 timestamp가 아닌 ISO-8601 형식으로 직렬화

        return new Jackson2JsonMessageConverter(objectMapper);
    }

    // Exchange 설정(라우팅 패턴을 사용하여 메세지 전달)
    @Bean
    public TopicExchange coupleLogExchange() {
        return new TopicExchange("couple-log.exchange", true, false);
    }

    // Queue 설정
    @Bean
    public Queue coupleLogQueue() {
        return QueueBuilder.durable("couple-log.exchanges")
                .withArgument("x-message-ttl", 30000)  // 메세지 TTl 30초(유효 시간)
                .withArgument("x-max-length", 1000)    // 최대 1000개 메세지 저장
                .build();
    }
    // Binding 설정
    @Bean
    public Binding documentChangesBinding(Queue documentChangesQueue, TopicExchange coupleLogExchange) {
        return BindingBuilder
                .bind(documentChangesQueue)
                .to(coupleLogExchange)
                .with("couple-log.*.exchanges");  // 라우팅 키 패턴
    }

    // RabbitMQ 관리를 위한 Admin 빈
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    private void retryMessage(RabbitTemplate rabbitTemplate,
                              Message message,
                              String exchange,
                              String routingKey,
                              int maxRetries) {
        int retries = 0;
        while (retries < maxRetries) {
            try {
                Thread.sleep(1000 * (retries + 1));
                rabbitTemplate.send(exchange, routingKey, message);
                log.info("메세지 재전송 success (시도 횟수 {})", retries + 1);
                return;
            } catch (Exception e) {
                log.error("재전송 실패 (시도 {}): {}", retries + 1, e.getMessage());
                retries++;
            }
        }
        log.error("최대 재시도 횟수 초과, 메세지 폐기됨");
    }
}
