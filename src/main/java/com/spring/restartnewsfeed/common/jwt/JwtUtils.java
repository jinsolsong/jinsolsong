package com.spring.restartnewsfeed.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Component
public class JwtUtils {

    //SECRET 의 경우 스프링설정파일(어플리케이션.프로퍼티즈)에서 주입받아 활용가능
    private static final String SECRET = "Leo-is-a-lizard";
    private static final String BEARER_PREFIX = "Bearer ";

    /*
    JWT 토큰 발급
    토큰 생성에 필요한 정보를 매개변수로 받아 JWT 토큰을 생성하고 반환
    - userId 학생식별자
    - role 학생 권한
    - dataYouWannaPut 토큰에 넣고싶은 데이터
     */
    public String createToken(Long userId, String email) throws UnsupportedEncodingException {
        //1. 토큰 서명에 활용될 알고리즘 설정 // 비밀키를 이용해 서명
        Algorithm algorithm = Algorithm.HMAC256(SECRET); // 헤더(Header)

        //2. 토큰 생성
        String token = JWT.create()
                .withSubject(userId.toString())
                .withClaim("email", email)
                //토큰 발급시간
                .withIssuedAt(new Date())
                //토큰 만료시간
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000))
                .sign(algorithm);
        return token;
    }

    /*
    토큰에서 유저 식별자를 추출
     */
    public Long extractUserIdFromToken(String token) throws UnsupportedEncodingException {
        //1. 토큰에 활용할 알고리즘 설정
        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        //2. 토큰 검증
        DecodedJWT decodedToken = JWT.require(algorithm)
                .build()
                .verify(token);

        //3. 토큰에서 원하는 정보 추출

        System.out.println("subject : " + decodedToken.getSubject());
        System.out.println("issued At : " + decodedToken.getIssuedAt());
        System.out.println("expires At : " + decodedToken.getExpiresAt());

        //4. 유저 식별자 반환
        String userId = decodedToken.getSubject();
        return Long.parseLong(userId);
    }

    /*
    베어러 토큰에서 학생 식별자를 추출함
     */
    public Long extractUserIdFromBearerToken(String bearerToken) throws UnsupportedEncodingException {
        //1. 토큰에 활용할 알고리즘
        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        //2. Bearer 토큰 추출
        String token = bearerToken.substring(BEARER_PREFIX.length()).trim();

        //3. 토큰 검증
        DecodedJWT decodedToken = JWT.require(algorithm)
                .build()
                .verify(token);

        //4. 토큰에서 원하는 정보 추출
        System.out.println("subject : " + decodedToken.getSubject());
        System.out.println("customField1 : " + decodedToken.getClaim("Leo").asString());
        System.out.println("issued At : " + decodedToken.getIssuedAt());
        System.out.println("expires At : " + decodedToken.getExpiresAt());

        //5. 유저 식별자 반환
        String userId = decodedToken.getSubject();
        return Long.parseLong(userId);
    }
}
