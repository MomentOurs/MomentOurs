package beyond.momentours.moment.query.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("momentMybatisConfiguration")
@MapperScan(basePackages = "beyond.momentours.moment.query.repository", annotationClass = Mapper.class)
public class MybatisConfiguration {
}
