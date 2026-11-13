package one.enchanter.rndevops.jester.playground.backend.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("one.enchanter.rndevops.jester.playground.backend.domain")
public class MyBatisConfig {
}