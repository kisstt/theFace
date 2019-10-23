package com.face.spider.sentence;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = {"com.face"}, exclude = {DataSourceAutoConfiguration.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@PropertySource(value = {"classpath:application.properties"})
@MapperScan(basePackages = "com.face.mapper")
public class PushSentenceApp {
    public static void main(String[] args) {
        SpringApplication.run(PushSentenceApp.class);
    }
}
