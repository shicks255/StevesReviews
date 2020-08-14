package com.steven.hicks.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@Aspect
public class OperationLogger {

    Logger logger = LoggerFactory.getLogger(OperationLogger.class);

    @Pointcut("@annotation(com.steven.hicks.aspects.Logged)")
    public void pointcut() {}

    @Before("pointcut()")
    public void beforeSearch(JoinPoint joinPoint) {
        StringBuilder logInfo = new StringBuilder();
        logInfo.append("class={} ");
        logInfo.append("method={} ");
        logInfo.append("arguments={} ");
        logInfo.append("date={} ");
        logInfo.append("time={}");

        Object[] args = new Object[]{
                joinPoint.getSignature().getDeclaringType().getName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs(),
                LocalDate.now(),
                LocalTime.now()
        };

        logger.info(logInfo.toString(), args);
        System.out.println("executing advice");
    }
}
