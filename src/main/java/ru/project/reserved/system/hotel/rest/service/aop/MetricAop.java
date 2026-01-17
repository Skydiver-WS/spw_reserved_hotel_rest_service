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
import ru.project.reserved.system.hotel.rest.service.service.main.MetricService;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Aspect
@Component
public class MetricAop {

    private final MetricService meterService;

    @Before("@annotation(metric)")
    @SneakyThrows
    public void before(JoinPoint joinPoint, Metric metric) {
        log.debug("Request metrics");
        Object o = joinPoint.getArgs();
        meterService.sendMetricStart(metric.type(), o, metric.description());
    }

    @AfterReturning(value = "@annotation(metric)", returning = "returning")
    @SneakyThrows
    public void after(JoinPoint joinPoint, Metric metric, Object returning) {
        log.debug("Response metrics aop");
        meterService.sendMetricEnd(metric.type(), returning, metric.description());
    }

    @AfterThrowing(value = "@annotation(metric)", throwing = "exception")
    public void throwApp(JoinPoint joinPoint, Metric metric, Exception exception) {
        log.debug("Exception metrics aop ", exception);
        meterService.sendExceptionMetric(metric.type(), exception, metric.description());
    }


}
