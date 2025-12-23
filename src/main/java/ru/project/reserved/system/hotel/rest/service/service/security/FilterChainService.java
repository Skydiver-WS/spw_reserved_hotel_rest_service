package ru.project.reserved.system.hotel.rest.service.service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.project.reserved.system.hotel.rest.service.dto.Redis;
import ru.project.reserved.system.hotel.rest.service.dto.type.MetricType;
import ru.project.reserved.system.hotel.rest.service.exception.RestException;
import ru.project.reserved.system.hotel.rest.service.service.main.MetricService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilterChainService extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserDetailsFromCacheService userDetailsFromCacheService;
    private final RedisTemplate<UUID, Redis> redisTemplate;
    private final MetricService metricService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            username = jwtService.extractUserName(token);
        } else {
            filterChain.doFilter(request, response);
            return;
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UUID keyId = UUID.fromString(jwtService.getHeader(token).getKeyId());
            Optional<Redis> optionalToken = Optional.ofNullable(redisTemplate.opsForValue().get(keyId));
            boolean tokenCache = optionalToken.isPresent();
            UserDetails userDetails;
            if (tokenCache) {
                log.info("Log get in cache");
                userDetails = userDetailsFromCacheService.loadUserByUsername(token);
            } else {
                log.info("Log get in secure database service");
                userDetails = userDetailsService.loadUserByUsername(username);
            }
            if (jwtService.isValidToken(token)) {
                authenticate(userDetails, request);

            }
        }
        filterChain.doFilter(request, response);
        if (response.getStatus() == HttpStatus.OK.value()){
            metricService.sendMetricEnd(MetricType.SIGN_IN_USER, null, "User signIn");
        } else if (response.getStatus() == HttpStatus.FORBIDDEN.value()){
            metricService.sendExceptionMetric(MetricType.ACCESS_DENIED,
                    new RestException(HttpStatus.FORBIDDEN, "Access decided"),
                    "User forbidden");
        } else if (response.getStatus() == HttpStatus.UNAUTHORIZED.value()){
            metricService.sendExceptionMetric(MetricType.NO_AUTH_USER,
                    new RestException(HttpStatus.UNAUTHORIZED, "Unauthorized"),
                    "User not authenticated");
        } else {
            metricService.sendExceptionMetric(MetricType.ERROR,
                    new RestException(HttpStatus.SERVICE_UNAVAILABLE, "Service unavailable"),
                    "System error");
        }

    }

    private void authenticate(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

    }

}
