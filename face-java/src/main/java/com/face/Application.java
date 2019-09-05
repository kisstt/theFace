package com.face;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application {
    @Bean
    public SpringUtils getSpringUtils() {
        return new SpringUtils();
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
