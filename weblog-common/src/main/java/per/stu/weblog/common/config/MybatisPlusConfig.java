package per.stu.weblog.common.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @description:  MybatisPlusConfig
 * @author: syl
 * @create: 2024-12-20 17:38
 **/
@Configuration
@MapperScan("per.stu.weblog.common.domain.mapper")
public class MybatisPlusConfig {
}
