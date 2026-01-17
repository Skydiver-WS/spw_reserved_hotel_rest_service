package ru.project.reserved.system.hotel.rest.service.configuration;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.project.reserved.system.hotel.rest.service.properties.SecurityProperties;
import ru.project.reserved.system.hotel.rest.service.service.security.FilterChainService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {

    private final FilterChainService jwtFilterService;
    private final SecurityProperties securityProperties;

    @Value("${server.port}")
    private int port;

    @Bean
    @Profile("!stub && prod")
    public PasswordEncoder dbPasswordEncoder() {
        return new BCryptPasswordEncoder(securityProperties.getLevelCrypto());
    }

    @Bean
    @Profile("stub")
    public PasswordEncoder dbPasswordEncoderNoDecoder() {
        log.info("Using no-op password encoder");
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @SneakyThrows
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       UserDetailsService detailsService,
                                                       PasswordEncoder passwordEncoder) {
        log.info("Start bean auth manager");
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(detailsService);
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(detailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        log.info("{} is successful", AuthenticationManagerBuilder.class.getName());
        return authenticationManagerBuilder.build();
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity, AuthenticationManager authenticationManager){
        log.info("Start conf {}", SecurityFilterChain.class.getName());
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationManager(authenticationManager)
                .addFilterBefore(jwtFilterService, UsernamePasswordAuthenticationFilter.class);
        log.info("Finish conf {}", SecurityFilterChain.class.getName());
        return httpSecurity.build();
    }

//    @Bean
//    public ServletWebServerFactory serverFactory(){
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory(){
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(redirectConnector());
//        return tomcat;
//    }
//
//    private Connector redirectConnector(){
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setPort(port);
//        connector.setSecure(false);
//        connector.setRedirectPort(8443);
//        return connector;
//    }
}