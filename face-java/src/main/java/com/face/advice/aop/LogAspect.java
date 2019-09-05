package com.face.advice.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.face.advice.aop..*(..))")
    private void allMethod(){

    }

    @Before("allMethod()")
    public void before(JoinPoint call){
        String className=call.getTarget().getClass().getName();
        String methodName=call.getSignature().getName();
        System.out.println(className+"."+methodName+"() start");
    }

    @After("allMethod()")
    public void after(JoinPoint call){
        String className=call.getTarget().getClass().getName();
        String methodName=call.getSignature().getName();
        System.out.println(className+"."+methodName+"() end");
    }
}
