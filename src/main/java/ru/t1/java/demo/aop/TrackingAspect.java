package ru.t1.java.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.TimeLimitExceedLog;
import ru.t1.java.demo.repository.TimeLimitExceedLogRepository;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;

@Async
@Slf4j
@Aspect
@Component
public class TrackingAspect {

    private static final AtomicLong START_TIME = new AtomicLong();
    private final TimeLimitExceedLogRepository timeLimitExceedLogRepository;

    public TrackingAspect(TimeLimitExceedLogRepository timeLimitExceedLogRepository) {
        this.timeLimitExceedLogRepository = timeLimitExceedLogRepository;
    }

    @Before("@annotation(ru.t1.java.demo.aop.Track)")
    public void logExecTime(JoinPoint joinPoint) throws Throwable {
        log.info("Старт метода: {}", joinPoint.getSignature().toShortString());
        START_TIME.addAndGet(System.currentTimeMillis());
    }

    @After("@annotation(ru.t1.java.demo.aop.Track)")
    public void calculateTime(JoinPoint joinPoint) {
        long afterTime = System.currentTimeMillis();
        long executionTime = afterTime - START_TIME.get();
        log.info("Время исполнения: {} ms", executionTime);

        Method executedMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        if (executionTime > executedMethod.getAnnotation(Track.class).threshold()) {
            timeLimitExceedLogRepository.save(new TimeLimitExceedLog(joinPoint.getSignature().toShortString(), executionTime));
        }

        START_TIME.set(0L);
    }

//    disabled so that other aspects could work
//    @Around("@annotation(ru.t1.java.demo.aop.Track)")
    public Object logExecTime(ProceedingJoinPoint pJoinPoint) {
        log.info("Вызов метода: {}", pJoinPoint.getSignature().toShortString());
        long beforeTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = pJoinPoint.proceed();//Important
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long afterTime = System.currentTimeMillis();
        log.info("Время исполнения: {} ms", (afterTime - beforeTime));
        return result;
    }

}
