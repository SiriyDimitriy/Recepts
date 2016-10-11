package com.anna.recept.aspect;

import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Logging for Pizza Delivery Service
 * @author Anna
 */
@Aspect
@Component
public class LoggingAspect {

    private Logger logger;

    @PostConstruct
    public void addAppender() {
        logger = Logger.getRootLogger();
    }

//    @Around("within(com.anna.recept.service.impl.*)")
//    public Object around(ProceedingJoinPoint joinPoint) {
//        try {
//            before();
//            Object retVal = joinPoint.proceed();
//            return retVal;
//
//        } catch (Throwable throwable) {
//            afterThrowing();
//            logger.error("Exception occurs");
//            logger.error(throwable.getMessage());
//            throwable.printStackTrace();
//            return null;
//        }
//    }

    @Before("within(com.anna.recept.service.impl.*)")
    public void round() {
            System.out.println("bla");

    }

    public void before() {
        logger.info("Before method......");
    }

    public void after() {
        logger.info("After method......");
    }

    public void afterThrowing() {
        logger.info("After throwing......");
    }

}
