package ru.project.reserved.system.hotel.rest.service.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import ru.project.reserved.system.hotel.rest.service.properties.GigaChatProp;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.util.List;
import java.util.UUID;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RestConfiguration {

    private final GigaChatProp gigaChatProp;
    private final ObjectMapper objectMapper;

    @Bean("main-rest")
    public RestClient restClient(){
        return RestClient.create();
    }

    @Bean("get-token")
    public RestClient restClientToken(@Qualifier("token-builder") RestClient.Builder builder) throws Exception {
        return builder.build();
    }

    @Bean("promt")
    public RestClient restClientPromt(@Qualifier("promt-builder") RestClient.Builder builder) throws Exception {
        return builder.build();
    }



    @Bean("token-builder")
    @Primary
    public RestClient.Builder restClientBuilderToken(@Qualifier("get-token-headers") HttpHeaders headers) throws Exception {
        log.info("Create RestClient bean token-builder");
        return RestClient.builder()
                .requestFactory(new JdkClientHttpRequestFactory(getHttpClient()))
                .defaultHeaders(h -> h.addAll(headers))
                .requestInterceptor(((request, body, execution) -> {
                    System.out.println("========== OUTGOING REQUEST ==========");
                    System.out.println("URI: " + request.getURI());
                    System.out.println("Method: " + request.getMethod());
                    System.out.println("Headers: " + request.getHeaders());
                    System.out.println("Body: " + objectMapper.writeValueAsString(new String(body, StandardCharsets.UTF_8)));
                    System.out.println("======================================");
                    return execution.execute(request, body);
                }));
    }

    @Bean("promt-builder")
    public RestClient.Builder restClientBuilderPromt() throws Exception {
        return RestClient.builder()
                .requestFactory(new JdkClientHttpRequestFactory(getHttpClient()))
                .requestInterceptor(((request, body, execution) -> {
                    System.out.println("========== OUTGOING REQUEST ==========");
                    System.out.println("URI: " + request.getURI());
                    System.out.println("Method: " + request.getMethod());
                    System.out.println("Headers: " + request.getHeaders());
                    System.out.println("Body: " + objectMapper.writeValueAsString(new String(body, StandardCharsets.UTF_8)));
                    System.out.println("======================================");
                    return execution.execute(request, body);
                }));
    }

    private HttpClient getHttpClient() throws Exception{
        KeyStore trustStore = KeyStore.getInstance("JKS");
        try (InputStream is =
                     new ClassPathResource("cert/russian-truststore.jks").getInputStream()) {
            trustStore.load(is, "123456".toCharArray());
        }
        TrustManagerFactory tmf =
                TrustManagerFactory.getInstance(
                        TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

        return HttpClient.newBuilder()
                .sslContext(sslContext)
                .build();
    }

    @Bean("get-token-headers")
    public HttpHeaders createGigaChatHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.add("RqUID", UUID.randomUUID().toString());
        headers.add(HttpHeaders.AUTHORIZATION, String.format("Basic %s", gigaChatProp.getAuthToken()));
        return headers;
    }
}
