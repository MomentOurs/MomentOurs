package beyond.momentours.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "beyond.momentours.*.query.repository", annotationClass = Mapper.class)
public class MybatisConfiguration {
}
