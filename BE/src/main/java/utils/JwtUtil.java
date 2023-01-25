package utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String createJwt(String userName, Long expiredMs){
        Claims claims = Jwts.claims();
        claims.put("userName",userName);//유저 정보를 클레임에 저장

        return Jwts.builder()//저장된 유저정보로 JWT 토큰을 생성

                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiredMs))
                .signWith(key)
                .compact();
    }
}
