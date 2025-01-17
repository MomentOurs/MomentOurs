package beyond.momentours.couple.query.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("coupleMybatisConfiguration")
@MapperScan(basePackages = "beyond.momentours.couple.query.repository", annotationClass = Mapper.class)
public class MybatisConfiguration {
}
