package com.wepat.aop.interceptor;

import com.wepat.exception.TokenExpiredException;
import com.wepat.exception.UnAuthorizedException;
import com.wepat.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtIntercepptor implements HandlerInterceptor {
    private static final String HEADER_AUTH = "token";
    private JwtUtil jwtUtil = new JwtUtil();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        final String token = request.getHeader(HEADER_AUTH);
         if (token == null)
            throw new UnAuthorizedException();

        if (jwtUtil.validateToken(token)) {
            log.info("token success");
//            log.info("verified Token : {}", token);
            return true;
        } else {
            log.info("token fail");
//            log.info("not verified token : {}", token);
            throw new TokenExpiredException();
        }
    }
}

