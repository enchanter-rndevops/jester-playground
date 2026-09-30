package dev.enchander.rndevops.jester.playground.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dev.enchander.rndevops.jester.playground.backend.interceptor.SessionValidationInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionValidationInterceptor sessionValidationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // セッションが正しいかどうかを検証するIntercepptor.
        // health, sessionなどの検証が不要なAPIは、このInterceptor内で除外している。
        registry.addInterceptor(sessionValidationInterceptor)
                .addPathPatterns("/api/v1/**");
    }
}
