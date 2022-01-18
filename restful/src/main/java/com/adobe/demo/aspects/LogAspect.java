package com.adobe.demo.aspects;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class LogAspect {
	Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	@Before("execution(* com.adobe.demo.service.*.*(..))")
	public void doLogBefore(JoinPoint jp) {
		logger.info("called : {} ", jp.getSignature());
		Object[] args = jp.getArgs();
		for(Object arg : args) {
			logger.info("argument {}", arg);
		}
	}
	
	@After("execution(* com.adobe.demo.service.*.*(..))")
	public void doLogAfter(JoinPoint jp) {
		logger.info("**************");
	}
	
	@Around("execution(* com.adobe.demo.service.*.*(..))")
	public Object doProfile(ProceedingJoinPoint jp) throws Throwable {
		Date startDate = new Date();
			Object ret = jp.proceed();
		Date endDate = new Date();
		logger.info("{} completed in {} ms", jp.getSignature()  , (endDate.getTime() - startDate.getTime()));
		return ret;
	}
}
