package ru.project.reserved.system.hotel.rest.service.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Aspect
@Component
public class MetricAop {

    private final MeterRegistry meterRegistry;

    @Before("@annotation(metric)")
    @SneakyThrows
    public void before(JoinPoint joinPoint, Metric metric) {
        log.info("Request metrics");
      Object o = joinPoint.getArgs();
        Counter counter = Counter.builder(metric.type().getType() + "_start")
                .description(metric.description())
                .tag("body", new ObjectMapper().writeValueAsString(o))
                .tag("request", joinPoint.getSignature().getName())
                .register(meterRegistry);
        counter.increment();

    }

    @AfterReturning(value = "@annotation(metric)", returning = "returning")
    @SneakyThrows
    public void after(JoinPoint joinPoint, Metric metric, Object returning) {
        log.info("Response metrics");
        if (Objects.nonNull(returning) && returning instanceof ResponseEntity<?> r){
            Counter counter = Counter.builder(metric.type().getType() + "_end")
                    .description(metric.description())
                    .tag("code", String.valueOf(r.getStatusCode().value()))
                    .tag("body", new ObjectMapper().writeValueAsString(r.getBody()))
                    .register(meterRegistry);
            counter.increment();
        }
    }

    @AfterThrowing(value = "@annotation(metric)", throwing = "exception")
    public void throwApp(JoinPoint joinPoint, Metric metric, Exception exception) {
        log.info("Exception metrics");
        HttpClientErrorException ex = null;
        if (exception instanceof HttpClientErrorException e){
            ex = (HttpClientErrorException) e;
        }
        Counter counter = Counter.builder(metric.type().getType() + "_error")
                .description(metric.description())
                .tag("code", Objects.nonNull(ex) ? String.valueOf(ex.getStatusCode().value()) : "500")
                .tag("error", exception.getMessage())
                .register(meterRegistry);
        counter.increment();
    }


}
