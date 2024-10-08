package com.UniSource.student_service;

import com.UniSource.student_service.client.TokenExtractor;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class FeignClientConfig {
    private final TokenExtractor tokenProvider; // or TokenExtractor

    @Autowired
    public FeignClientConfig(TokenExtractor tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                String token = tokenProvider.extractToken(); // or tokenExtractor.extractToken()
                if (token != null) {
                    template.header("Authorization", "Bearer " + token);
                }
            }
        };
    }
}
