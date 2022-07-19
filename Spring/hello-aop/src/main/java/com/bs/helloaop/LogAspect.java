package com.bs.helloaop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
@Slf4j
public class LogAspect {

    // LogExecutionTime 어노테이션이 붙은 메소드만
    @Around("@annotation(LogExecutionTime)")
    public Object executionTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        return executionTime(joinPoint);
    }

    //HelloAopController 모든 메소드에 적용
    @Around("execution(* com.bs.helloaop.HelloAopController.*(..))")
    public Object executionTimeLogAll(ProceedingJoinPoint joinPoint) throws Throwable {

        return executionTime(joinPoint);

    }


    private Object executionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();

        log.info("-----START " + joinPoint.getSignature().getName()+ " method" + "------");

        stopWatch.start();

        //target method 실행
        Object proceed = joinPoint.proceed();

        stopWatch.stop();

        log.info("-----END------");

        log.info("Performance time : " + stopWatch.getTotalTimeMillis()+"(ms)");

        return proceed;
    }

}
