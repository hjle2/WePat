package com.wepat.aop.interceptor;

import com.wepat.exception.TokenExpiredException;
import com.wepat.exception.UnAuthorizedException;
import com.wepat.util.JwtUtil;
import org.apache.el.parser.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtIntercepptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(JwtIntercepptor.class);
    private static final String HEADER_AUTH = "token";
    private JwtUtil jwtUtil = new JwtUtil();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        final String token = request.getHeader(HEADER_AUTH);
         if (token == null)
            throw new UnAuthorizedException();

        final String memberId = jwtUtil.getUserId(token);
        request.getSession().setAttribute("memberId", memberId);

        if (jwtUtil.validateToken(token)) {
            logger.info("verified Token : {}", token);
            return true;
        } else {
            logger.info("not verified token : {}", token);
            throw new TokenExpiredException();
        }
    }
}

