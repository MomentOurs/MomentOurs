package beyond.momentours.announcement.query.config;


import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("departmentMybatisConfiguration")
@MapperScan(basePackages = "beyond.momentours.announcement.query.repository", annotationClass= Mapper.class)
public class MyBatisConfiguration {
}
