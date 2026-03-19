package ru.project.reserved.system.hotel.rest.service.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.project.reserved.system.hotel.rest.service.dto.Redis;
import ru.project.reserved.system.hotel.rest.service.web.request.PromtRq;
import ru.project.reserved.system.hotel.rest.service.web.response.GigaChatRs;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
@Aspect
@Component
public class CookieAop {

    private final RedisTemplate<UUID, Redis> redisTemplate;

    @Around("@annotation(cookie)")
    public Object before(ProceedingJoinPoint joinPoint, Cookie cookie) throws Throwable {
        Object rs = joinPoint.proceed();
        ResponseEntity<GigaChatRs> response = (ResponseEntity<GigaChatRs>) rs;
        GigaChatRs gigaChatRs = response.getBody();
        if (Objects.nonNull(gigaChatRs) && !gigaChatRs.isResult()) {
            HttpServletResponse servletResponse = getHttpServletResponse();

            if (servletResponse != null) {
                // Создаем куку
                jakarta.servlet.http.Cookie responseCookie = new jakarta.servlet.http.Cookie(
                        "sessionId",
                        UUID.randomUUID().toString()
                );

                // Настраиваем куку
                responseCookie.setHttpOnly(true);  // для безопасности
                responseCookie.setSecure(true);    // для HTTPS
                responseCookie.setPath("/");
                responseCookie.setMaxAge(3600);    // время жизни в секундах

                // Добавляем куку в ответ
                servletResponse.addCookie(responseCookie);
                log.info("Cookie set: sessionId={}", responseCookie.getValue());
            }

        }
        return rs;
    }

    private HttpServletResponse getHttpServletResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getResponse();
        }
        return null;
    }
}
