package ru.project.reserved.system.hotel.rest.service.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.project.reserved.system.hotel.rest.service.dto.Redis;
import ru.project.reserved.system.hotel.rest.service.properties.GigaChatProp;
import ru.project.reserved.system.hotel.rest.service.web.request.PromtRq;
import ru.project.reserved.system.hotel.rest.service.web.response.GigaChatRs;


import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Aspect
@Component
public class CookieAop {

    private final RedisTemplate<UUID, Redis> redisTemplate;
    private final GigaChatProp prop;

    @Around("@annotation(cookie)")
    public Object before(ProceedingJoinPoint joinPoint, Cookie cookie) throws Throwable {
        log.info("Start AOP");

        // 1. ВЫПОЛНЯЕМ МЕТОД ТОЛЬКО ОДИН РАЗ В САМОМ НАЧАЛЕ
        Object [] args = joinPoint.getArgs();
        Pageable pageable = Arrays.stream(args)
                .filter(a -> a instanceof Pageable)
                .map(a -> (Pageable) a)
                .findFirst()
                .orElse(null);


        // 2. Ищем PromtRq В АРГУМЕНТАХ (не в результате!)
        PromtRq rq = Arrays.stream(args)
                .filter(o -> o instanceof PromtRq)
                .map(o -> (PromtRq) o)
                .findFirst()
                .orElse(null);

        // 3. Обрабатываем случай с Redis (если есть sessionId в cookie)
        String sessionId = null;
        if (Objects.nonNull(rq)) {
            HttpServletRequest httpServletRequest = getCurrentHttpRequest();
            if (Objects.nonNull(httpServletRequest)) {
                log.info("Inject sessionId from Cookie");
                jakarta.servlet.http.Cookie[] ck = httpServletRequest.getCookies();

                if (ck != null) {
                    sessionId = Arrays.stream(ck)
                            .filter(c -> c.getName().equals("sessionId"))
                            .map(jakarta.servlet.http.Cookie::getValue)
                            .findFirst().orElse("");

                    if (!sessionId.isEmpty()) {
                        try {
                            Redis redis = redisTemplate.opsForValue().get(UUID.fromString(sessionId));
                            if (redis != null) {
                                log.info("Set previous response Giga chat");
                                GigaChatRs gigaChatRs = redis.getGigaChatRs();
                                rq.setGigaChatRsInCache(gigaChatRs);
                            }
                        } catch (IllegalArgumentException e) {
                            log.error("Invalid sessionId format: {}", sessionId);
                        }
                    }
                }
            }
        }
        Object result = joinPoint.proceed(List.of(rq, pageable).toArray());

        // 4. Обрабатываем ответ (result уже есть!)
        if (result instanceof ResponseEntity) {
            ResponseEntity<GigaChatRs> response = (ResponseEntity<GigaChatRs>) result;
            GigaChatRs gigaChatRs = response.getBody();

            if (Objects.nonNull(gigaChatRs) && !gigaChatRs.isResult()) {
                HttpServletResponse servletResponse = getHttpServletResponse();
                if (servletResponse != null) {
                    sessionId = Strings.isBlank(sessionId) ? UUID.randomUUID().toString() : sessionId;

                    // Создаем куку
                    jakarta.servlet.http.Cookie responseCookie = new jakarta.servlet.http.Cookie(
                            "sessionId",
                            sessionId
                    );

                    // Настраиваем куку
                    responseCookie.setHttpOnly(true);
                    responseCookie.setSecure(true);
                    responseCookie.setPath("/");
                    responseCookie.setMaxAge(!gigaChatRs.isResult() ? Math.toIntExact(prop.getCookieLive().toMillis()) :
                            0);

                    // Добавляем куку в ответ
                    servletResponse.addCookie(responseCookie);
                    log.info("Cookie set: sessionId={}", responseCookie.getValue());

                    // Сохраняем в Redis
                    if (gigaChatRs.isResult()) {
                        redisTemplate.opsForValue().getAndDelete(UUID.fromString(sessionId));
                    } else {
                        redisTemplate.opsForValue().set(
                                UUID.fromString(sessionId),
                                Redis.builder().gigaChatRs(gigaChatRs).build(),
                                prop.getCookieLive()
                        );
                    }
                }
            }
        }

        // 5. ВОЗВРАЩАЕМ ОРИГИНАЛЬНЫЙ РЕЗУЛЬТАТ
        return result;
    }

    private HttpServletResponse getHttpServletResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getResponse();
        }
        return null;
    }

    private HttpServletRequest getCurrentHttpRequest() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }
}
