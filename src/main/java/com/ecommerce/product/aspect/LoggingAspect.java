package com.ecommerce.product.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class LoggingAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	
	@Pointcut("@annotation(Loggable)")
	public void executeLogging() {
		
	}
//	@Before("executeLogging()")
//	public void logBeforeMethodCall(JoinPoint joinPoint) {
//		
//		StringBuilder msg = new StringBuilder("START: Method: ");
//		
//		msg.append(joinPoint.getSignature().getName());
//		msg.append(joinPoint.getTarget().getClass().getSimpleName());
//		
//		Object[] argsObj = joinPoint.getArgs();
//		if(argsObj != null && argsObj.length > 0) {
//			msg.append(". Args[ = | " );
//			Arrays.asList(argsObj).forEach(arg->
//				msg.append(arg + " | ")
//			);
//			msg.append(" ]." );
//		}
//		logger.info("{}",msg);
//	}
//	@After("executeLogging()")
//	public void logAfterMethodCall(JoinPoint joinPoint) {
//		
//		StringBuilder msg = new StringBuilder("END: Method: ");
//		
//		msg.append(joinPoint.getSignature().getName());
//		msg.append(joinPoint.getTarget().getClass().getSimpleName());
//		
//		
//		logger.info("{}",msg);
//	}
	@Around("executeLogging()")
	public Object logBeforeMethodCall(ProceedingJoinPoint joinPoint) {
		
		String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        // Log method entry and arguments
        logger.info("Entering method: {} with arguments: {}", methodName, args);

        // Proceed with the actual method call
        Object result = null;
		try {
			result = joinPoint.proceed();
		} catch (Throwable e) {
			logger.error("error at joinPoint.proceed {0}",e);
		}

        // Log the method's return value
        logger.info("Exiting method: {} with return value: {}", methodName, result);

        // Return the result back to the caller
        return result;
	}
}
