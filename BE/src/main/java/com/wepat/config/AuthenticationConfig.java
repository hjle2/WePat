package com.wepat.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class AuthenticationConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeHttpRequests()
                //.requestMatchers("/signin").permitAll()//로그인과 회원가입은 인증없이도 가능하게!
                //.requestMatchers(HttpMethod.POST,"/api/v1/**").authenticated()//로그인 회원가입을 제외한 기능은 인증요청
//                .mvcMatchers("/signup").permitAll()
//                .mvcMatchers(HttpMethod.POST,"/mainpage").authenticated()
                .antMatchers("/signup").permitAll()
                .antMatchers(HttpMethod.POST,"/mainpage").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();
    }
}
