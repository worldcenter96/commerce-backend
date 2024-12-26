package com.sparta.common.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Aspect
@Component
public class LoggingAspect {

    // 포인트컷 정의
    @Pointcut("execution(* com.sparta..controller..*(..))")
    public void controllerMethods() {}

    // @Around 어노테이션을 사용하여 메서드 실행 전후에 로깅을 추가
    @Around("controllerMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        // 메서드 실행 전: traceId 가져오기, 없으면 새로 생성
        String traceId = MDC.get("traceId");
        if (traceId == null) {
            traceId = UUID.randomUUID().toString(); // traceId가 없으면 새로 생성
            MDC.put("traceId", traceId); // MDC 에 traceId 설정
        }

        String methodName = joinPoint.getSignature().getName();
        String methodArgs = Arrays.toString(joinPoint.getArgs());

        // 요청 시작 시간 기록
        Long startTime = System.currentTimeMillis();

        // 로그: 메서드 호출 전
        if (traceId != null) {
            System.out.println("[TRACE_ID: " + traceId + "] Executing method: " + methodName + " with args: " + methodArgs);
        }

        // 메서드 실행
        Object result = joinPoint.proceed();

        // 요청 종료 시간 기록
        Long endTime = System.currentTimeMillis();
        Long duration = endTime - startTime;

        // 로그: 메서드 호출 후
        if (traceId != null) {
            System.out.println("[TRACE_ID: " + traceId + "] Method: " + methodName + "completed in " + duration + " ms");
        }

        return result;
    }
}
