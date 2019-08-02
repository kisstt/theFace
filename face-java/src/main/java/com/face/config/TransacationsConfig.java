package com.face.config;

import com.face.utils.DataSourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * @Author: admin
 * @Description:
 * @Date: Created in 上午11:28 2018/4/26
 */
@Aspect
@Configuration
@Slf4j
@Primary
public class TransacationsConfig {

    @Value("${app.name:face}")
    public String appName;

    @Bean(name = "txAdvice")
    public TransactionInterceptor txAdvice(@Qualifier("txManager") PlatformTransactionManager transactionManager) {
        return DataSourceUtils.getTransactionInterceptor(transactionManager);
    }

    @Bean
    public Advisor txAdvisor(@Qualifier("txAdvice") TransactionInterceptor txAdvice) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution (* com.face." + appName.replace("-", ".") + ".service..*.*(..))");
        return new DefaultPointcutAdvisor(pointcut, txAdvice);
    }

}