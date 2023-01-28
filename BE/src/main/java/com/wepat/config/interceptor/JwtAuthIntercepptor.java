package com.wepat.config.interceptor;

import com.wepat.member.repository.MemberRepository;
import com.wepat.exception.UnAuthorizedException;
import com.wepat.member.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtAuthIntercepptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthIntercepptor.class);
    private static final String HEADER_AUTH = "auth-token";

    @Autowired
    private JwtService jwtService;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String token = request.getHeader(HEADER_AUTH);
        if (token != null && jwtService.verifyToken(token)) {
            logger.info("verified Token : {}", token);
            return true;
        } else {
            logger.info("not verified token : {}", token);
            throw new UnAuthorizedException();
        }
    }
}

