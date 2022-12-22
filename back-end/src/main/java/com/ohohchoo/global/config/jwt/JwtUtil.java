package com.ohohchoo.global.config.jwt;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {

    private static final String SALT = "ohohchoo";
    private static final long TOKEN_VALID_TIME = 60 * 60 * 1000L;

    // 토큰 생성
    public String createToken(String claimId, Long userId) throws UnsupportedEncodingException {
        // 유효기간 2시간
        Date now = new Date();
        return Jwts.builder().setHeaderParam("alg", "HS256").setHeaderParam("typ", "JWT").claim(claimId, userId)
                .setExpiration(new Date(now.getTime() + TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, SALT.getBytes("UTF-8")).compact();
    }

    // 유효성 검사
    public void valid(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException,
            SignatureException, IllegalArgumentException, UnsupportedEncodingException {
        Jwts.parser().setSigningKey(SALT.getBytes("UTF-8")).parseClaimsJws(token);

    }

    // 토큰 정보 가져오기
    public Long getTokenInfo(String token) {
        Jws<Claims> claims = null;
        Long userId = null;
        claims = Jwts.parser().setSigningKey(SALT.getBytes()).parseClaimsJws(token);
        userId = Long.valueOf((int) claims.getBody().get("userId"));

        return userId;
    }

}