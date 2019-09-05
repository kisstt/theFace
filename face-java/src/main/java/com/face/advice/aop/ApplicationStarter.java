package com.face.advice.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},
        scanBasePackages = "com.face.advice.aop")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ApplicationStarter {

    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(ApplicationStarter.class, args);
        TestBean testBean = ac.getBean(TestBean.class);
        String stu = testBean.getStu();
    }
}
