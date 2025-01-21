package beyond.momentours.config;

import beyond.momentours.couple.command.domain.aggregate.entity.MatchingCode;
import beyond.momentours.couplelog.command.domain.aggregate.session.EditSessionInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${REDIS_HOST}")
    private String host;

    @Value("${REDIS_PORT}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Redis 연결 설정
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(host, port);
        return new LettuceConnectionFactory(redisConfig);
    }

    @Bean(name = "matchingCodeTemplate")
    public RedisTemplate<String, MatchingCode> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, MatchingCode> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

    @Bean(name = "editSessionTemplate")
    public RedisTemplate<String, EditSessionInfo> editSessionTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, EditSessionInfo> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // JSON 직렬화를 위한 설정
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();

        template.setValueSerializer(jsonSerializer);
        template.setHashValueSerializer(jsonSerializer);

        template.afterPropertiesSet();
        return template;
    }
}