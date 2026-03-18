package ru.project.reserved.system.hotel.rest.service.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
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
    public Object around(ProceedingJoinPoint joinPoint, Cookie cookie) throws Throwable {

        PromtRq rq = Arrays.stream(joinPoint.getArgs()).filter(o -> o instanceof PromtRq)
                .map(o -> (PromtRq) o).findFirst().orElse(null);

        if (Objects.isNull(rq)) {
            Object result = joinPoint.proceed();
            ResponseEntity<GigaChatRs> rs = (ResponseEntity<GigaChatRs>) result;
            GigaChatRs gigaChatRs = rs.getBody();
            if (!gigaChatRs.isResult()){
                UUID sessionId = UUID.randomUUID();
                redisTemplate.opsForValue().set(sessionId, Redis.builder()
                        .gigaChatRs(gigaChatRs)
                        .build());
            }
            return result;
        }


        return rq;
//        if(Strings.isBlank(cookieValue)){
//            return joinPoint.proceed();
//        }
//
//        Optional<Redis> redis = Optional.ofNullable(redisTemplate.opsForValue().getAndDelete(UUID.fromString(cookieValue)));
//        return redis.get();

    }

    private String injectCookie(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(Objects.isNull(attributes)){
            return "";
        }
        HttpServletRequest request = attributes.getRequest();
        jakarta.servlet.http.Cookie[] cookies = request.getCookies();
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals("session"))
                .findFirst()
                .map(jakarta.servlet.http.Cookie::getValue)
                .orElse("");
    }
}
