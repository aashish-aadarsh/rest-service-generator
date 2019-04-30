package com.devop.aashish.java.myapplication.configuration.aop.processor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * This class is the implementation of LogMethodInfo.
 */
@Aspect
@Component
public class LogMethodInfoProcessor {

    private static final String POINT_CUT = "publicMethodPointcut() && within(@com.devop.aashish.java.myapplication.configuration.aop.annotation.LogMethodInfo *)";
    private static Logger logger = LoggerFactory.getLogger(LogMethodInfoProcessor.class);

    /**
     * @param jointPoint for proxy invoked
     *                   This method logs the entry before any public method is executed with
     *                   the input params
     */
    @Before(value = POINT_CUT)
    public void logEntryInMethod(JoinPoint jointPoint) {
        logger.debug("-->Entry in Method {} with param {}",
                jointPoint.getSignature().toShortString(),
                Arrays.toString(jointPoint.getArgs()));
    }

    /**
     * @param jointPoint for proxy invoked
     *                   This method logs the exit after any public method is executed
     */
    @After(value = POINT_CUT)
    public void logExitFromMethod(JoinPoint jointPoint) {
        logger.debug("<--Exit From Method {}.",
                jointPoint.getSignature().toShortString());
    }

    /**
     * Point cut for all public methods
     */
    @Pointcut("execution(public * *(..))")
    public void publicMethodPointcut() {

    }
}
