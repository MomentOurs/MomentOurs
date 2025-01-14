package beyond.momentours.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("MybatisConfiguration")
@MapperScan(basePackages = "beyond.momentours.config", annotationClass = Mapper.class)
public class MybatisConfiguration {
}
