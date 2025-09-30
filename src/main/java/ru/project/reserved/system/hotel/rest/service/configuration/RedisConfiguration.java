package ru.project.reserved.system.hotel.rest.service.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import ru.project.reserved.system.hotel.rest.service.dto.Redis;
import ru.project.reserved.system.hotel.rest.service.properties.RedisProperties;

import java.util.Date;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RedisConfiguration {
    private final RedisProperties redisProperties;

    @Bean
    public RedisTemplate<UUID, Redis> redisTemplate(RedisConnectionFactory connectionFactory) {
        log.info("Redis connection factory");
        RedisTemplate<UUID, Redis> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    @Primary
    public RedisConnectionFactory redisConnectionFactory() {
        log.info("Creating connect");
        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
    }
    @Bean
    public Date createdate(){
        log.info(new Date().toString());
        return new Date();
    }

}
