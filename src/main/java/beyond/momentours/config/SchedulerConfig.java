package beyond.momentours.config;

import beyond.momentours.date_course.query.repository.DateCourseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.data.redis.core.RedisCallback;

@Slf4j
@Configuration
@EnableScheduling
public class SchedulerConfig {

    private final RedisTemplate<String, String> redisTemplate;
    private final DateCourseMapper dateCourseDAO;

    public SchedulerConfig(RedisTemplate<String, String> redisTemplate, DateCourseMapper dateCourseDAO) {
        this.redisTemplate = redisTemplate;
        this.dateCourseDAO = dateCourseDAO;
    }

    @Scheduled(fixedRate = 60000)
    public void syncViewCountsToDatabase() {
        ScanOptions options = ScanOptions.scanOptions().match("course:view:*").count(1000).build();

        try {
            redisTemplate.execute((RedisCallback<Void>) connection -> {
                try (Cursor<byte[]> cursor = connection.keyCommands().scan(options)) {
                    while (cursor.hasNext()) {
                        String key = new String(cursor.next());
                        Long courseId = Long.parseLong(key.split(":")[2]);
                        String viewCountStr = redisTemplate.opsForValue().get(key);
                        Long viewCount = (viewCountStr != null) ? Long.parseLong(viewCountStr) : 0L;

                        dateCourseDAO.incrementViewCountInDB(courseId, viewCount);
                        redisTemplate.delete(key);
                    }
                }
                return null;
            });
        } catch (Exception e) {
            log.error("뷰 카운트 동기화 중 오류가 발생했습니다. 오류 내용: ", e);
        }
    }
}
