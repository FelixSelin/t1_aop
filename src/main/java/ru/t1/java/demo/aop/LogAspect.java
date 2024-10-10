package ru.t1.java.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.Client;
import ru.t1.java.demo.model.DataSourceErrorLog;
import ru.t1.java.demo.repository.DataSourceErrorLogRepository;

import java.util.Arrays;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Aspect
@Component
@Order(0)
public class LogAspect {

    private final DataSourceErrorLogRepository dataSourceErrorLogRepository;

    public LogAspect(DataSourceErrorLogRepository dataSourceErrorLogRepository) {
        this.dataSourceErrorLogRepository = dataSourceErrorLogRepository;
    }

    @Pointcut("within(ru.t1.java.demo.*)")
    public void loggingMethods() {

    }

    @Before("@annotation(LogExecution)")
    @Order(0)
    public void logAnnotationBefore(JoinPoint joinPoint) {
        log.info("ASPECT BEFORE ANNOTATION: Call method: {}", joinPoint.getSignature().getName());
    }

//    @Before("execution(public * ru.t1.java.demo.service.ClientService.*(..))")
//    public void logBefore(JoinPoint joinPoint) {
//        log.error("ASPECT BEFORE: Call method: {}", joinPoint.getSignature().getName());
//    }


    @AfterThrowing(pointcut = "@annotation(LogException)", throwing = "thrownException")
    @Order(0)
    public void logExceptionAnnotation(JoinPoint joinPoint, Exception thrownException) {
        System.err.println("ASPECT EXCEPTION ANNOTATION: Logging exception: {}" + joinPoint.getSignature().getName());
        DataSourceErrorLog errorLog = DataSourceErrorLog.builder()
                .methodSignature(joinPoint.getSignature().getName())
                .message(thrownException.getMessage())
                .stackTrace(Arrays.toString(thrownException.getStackTrace()))
                .build();
        dataSourceErrorLogRepository.save(errorLog);
    }

    @AfterReturning(
            pointcut = "@annotation(HandlingResult)",
            returning = "result")
    public void handleResult(JoinPoint joinPoint, List<Client> result) {
        log.info("В результате выполнения метода {}", joinPoint.getSignature().toShortString());
//        log.info("получен результат: {} ", result);
        log.info("Подробности: \n");

        result = isNull(result) ? List.of() : result;

    }

}
