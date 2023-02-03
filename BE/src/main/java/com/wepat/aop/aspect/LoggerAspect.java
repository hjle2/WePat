package com.wepat.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {

    @Around("execution(* com.wepat..*..*Controller.*(..)) || execution(* com.wepat..*..*Repository.*(..))")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

        String name = joinPoint.getSignature().getDeclaringTypeName();
        return joinPoint.proceed();
    }

}
