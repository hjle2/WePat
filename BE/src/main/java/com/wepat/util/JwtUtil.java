package com.wepat.util;

import com.google.gson.Gson;
import com.wepat.exception.TokenExpiredException;
import com.wepat.exception.UnAuthorizedException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {
    private static final String SALT = "wepatsecretkeythisissecretsecretkeybyhj";
    private static final long ACCESS_TOKEN_EXPIRE_MINUTES = 1000L * 60 * 60; // 시간 단위
    private static final long REFRESH_TOKEN_EXPIRE_MINUTES = 1000L * 60 * 60 * 24 * 7; // 주단위
    public static <T> String createAccessToken(String key, T data) {
        return create(key, data, "access-token", ACCESS_TOKEN_EXPIRE_MINUTES);
    }

    public static <T> String createRefreshToken(String key, T data) {
        return create(key, data, "refresh-token", REFRESH_TOKEN_EXPIRE_MINUTES);
    }

    //Token 발급
    /**
     * key : Claim에 셋팅될 key 값
     * data : Claim에 셋팅 될 data 값
     * subject : payload에 sub의 value로 들어갈 subject값
     * expire : 토큰 유효기간 설정을 위한 값
     * jwt 토큰의 구성 : header+payload+signature
     */
    public static <T> String create(String key, T data, String subject, long expire) {
        Date exp = new Date();
        exp.setTime(exp.getTime() + expire);

        String jwt = Jwts.builder()
                // Header 설정 : 토큰의 타입, 해쉬 알고리즘 정보 세팅.
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis()) // 생성 시간
                // Payload 설정 : 유효기간(Expiration), 토큰 제목 (Subject), 데이터 (Claim) 등 정보 세팅.
                .setExpiration(exp) // 토큰 유효기간
                .setSubject(subject) // 토큰 제목 설정 ex) access-token, refresh-token
                .claim(key, data) // 저장할 데이터
                // Signature 설정 : secret key를 활용한 암호화.
                .signWith(SignatureAlgorithm.HS256, SALT.getBytes())
                .compact(); // 직렬화 처리.
        return jwt;
    }

    public static Map<String, Object> get(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String jwt = request.getHeader("access-token");
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
        } catch (Exception e) {
//			if (logger.isInfoEnabled()) {
//				e.printStackTrace();
//			} else {
            log.error(e.getMessage());
//			}
            throw new UnAuthorizedException();
//			개발환경
//			Map<String,Object> testMap = new HashMap<>();
//			testMap.put("userid", userid);
//			return testMap;
        }
        Map<String, Object> value = claims.getBody();
        log.info("value : {}", value);
        return value;
    }

    public static String getUserId
            (String token) {
        Claims jws = Jwts.parser()
                .setSigningKey(SALT.getBytes())
                .parseClaimsJws(token).getBody();

        String memberId = jws.get("memberId").toString();
        return memberId;
    }
    public static String getUserIdByHttpRequest(HttpServletRequest request) {
        String token = request.getHeader("token");
        return getUserId(token);
    }
    // Signature 설정에 들어갈 key 생성.
    private byte[] generateKey() {
        byte[] key = null;
        try {
            // charset 설정 안하면 사용자 플랫폼의 기본 인코딩 설정으로 인코딩 됨.
            key = SALT.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            if (log.isInfoEnabled()) {
                e.printStackTrace();
            } else {
                log.error("Making JWT Key Error ::: {}", e.getMessage());
            }
        }

        return key;
    }

    public static boolean validateToken(String jwt) {
        try {
//			Json Web Signature? 서버에서 인증을 근거로 인증정보를 서버의 private key로 서명 한것을 토큰화 한것
//			setSigningKey : JWS 서명 검증을 위한  secret key 세팅
//			parseClaimsJws : 파싱하여 원본 jws 만들기
            Claims claims = Jwts.parser().setSigningKey(SALT.getBytes()).parseClaimsJws(jwt).getBody();
            return true;
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
