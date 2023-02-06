package com.wepat.config;

import com.wepat.aop.interceptor.JwtIntercepptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // login auth
        registry.addInterceptor(new JwtIntercepptor())
                .addPathPatterns("/member/**")
                .addPathPatterns("/schedule/**")
                .addPathPatterns("/calendar/**")
                .addPathPatterns("/pet/**")
                .addPathPatterns("/sns/**")
                .addPathPatterns("/photo/**")
                .addPathPatterns("/finance/**")
                .addPathPatterns("/alarm/**")
                .excludePathPatterns("/member/signin/**")
                .excludePathPatterns("/member/signup/**")
                .excludePathPatterns("/member/logout")
                .excludePathPatterns("/member/findid")
                .excludePathPatterns("/member/findpwd")
                .excludePathPatterns("/error/*");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}
