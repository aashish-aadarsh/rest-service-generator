package com.devop.aashish.java.myapplication.configuration.aop.processor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * This class is the implementation of LogExecutionTime.
 */
@Aspect
@Component
public class LogExecutionProcessor {

    private static Logger logger = LoggerFactory.getLogger(LogExecutionProcessor.class);

    /**
     * @param joinPoint param to get proxy info
     * @return the processed object to give callback to calling proxy
     * @throws Throwable error in case some system error occurred
     *
     * This method logs the time taken to execute a method. Generally used in controller to check time taken
     * to process a request.
     */
    @Around(value = "publicMethodPointcut() && within(@com.devop.aashish.java.myapplication.configuration.aop.annotation.LogExecutionTime *)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        logger.info("--> Method {} executed in {} ms", joinPoint.getSignature().toShortString(), executionTime);
        return proceed;
    }

    /**
     * Point cut for all public methods
     */
    @Pointcut("execution(public * *(..))")
    public void publicMethodPointcut() {

    }
}
