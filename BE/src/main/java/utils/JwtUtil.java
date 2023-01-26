package utils;

import com.google.api.Authentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String createJwt(String memberId, String subject, Long expiredMs) {
        Claims claims = Jwts.claims();
        claims.put("memberId", memberId);//유저 정보를 클레임에 저장

        return Jwts.builder()//저장된 유저정보로 JWT 토큰을 생성

                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(key)
                .setSubject(subject)
                .compact();
    }


    //check Token
    public static boolean checkToken(String jwt) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
            return true;//만료되지않은 토큰이다.
        } catch (Exception e) {
            return false;
        }
    }

    // Jwt 토큰에서 아이디 추출
//    public boolean checkToken(String memberId) {//복호화 코드
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(key)
//                    .parseClaimsJws(token)
//                    .getBody();
//            return true;//토큰에서 페이로드 추출이 정상적으로 성공하면 유효한 토큰
//        } catch (Exception e) {
//            System.out.println("토큰이 만료됨");
//            return false;//토큰이 만료되어 추출이 불가하면 false;
//        }
//    }

}
