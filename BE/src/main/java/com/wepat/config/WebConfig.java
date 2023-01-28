package com.wepat.config;

import com.wepat.aop.interceptor.JwtIntercepptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private String[] INTERCEPTOR_WRITE_LIST = {
            "/member/signup/**",
            "/member/signin/**",
            "/swagger-ui/index.html",
            "/swagger-ui/index.html#/**",
            "/swagger-resource/**",
            "/v2/api-docs/**"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // login auth
        registry.addInterceptor(new JwtIntercepptor())
                .addPathPatterns("/member/**")
                .addPathPatterns("/schedule/**")
                .addPathPatterns("/pet/**")
                .addPathPatterns("/sns/**")
                .addPathPatterns("/photo/**")
                .addPathPatterns("/finance/**")
                .excludePathPatterns("/member/signup")
                .excludePathPatterns("/member/logout")
                .excludePathPatterns("/member/signin");

    }
}
