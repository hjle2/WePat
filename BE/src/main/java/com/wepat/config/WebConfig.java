package com.wepat.config;

import com.wepat.aop.interceptor.JwtIntercepptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
                .excludePathPatterns("/member/signin")
                .excludePathPatterns("/member/logout")
                .excludePathPatterns("/member/signup")
                .excludePathPatterns("/member/socialsignup")
                .excludePathPatterns("/member/socialsignin")
                .excludePathPatterns("/member/findid")
                .excludePathPatterns("/member/findpwd");

    }
}
