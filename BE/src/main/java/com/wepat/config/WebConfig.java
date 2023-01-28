package com.wepat.config;

import com.wepat.config.interceptor.JwtAuthIntercepptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private String[] INTERCEPTOR_WRITE_LIST = {
            "/signup/**",
            "/signin/**",
            "/swagger-ui/**",
            "/swagger-resource/**",
            "/v2/api-docs/**"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // login auth
//        registry.addInterceptor(new JwtAuthIntercepptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns(INTERCEPTOR_WRITE_LIST);
    }
}
