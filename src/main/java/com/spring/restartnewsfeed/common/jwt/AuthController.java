//package com.spring.restartnewsfeed.common.jwt;
//
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.UnsupportedEncodingException;
//
//@Slf4j
//@RestController
//@RequestMapping("/auth")
//@RequiredArgsConstructor
//public class AuthController {
//    // TODO 필터 만들어와요 JWT 인증 필터
//    private final JwtUtils jwtUtils;
//
//    /*
//        로그인 API
//        유저의 이메일, 패스워드를 받아 로그인을 시도
//        로그인 성공시 JWT 토큰을 생성해 클라이언트에게 반환
//         */
//    @GetMapping("/login")
//    public String loginAPI() throws UnsupportedEncodingException {
//        //1. 로그인을 했다고 가정
//        //2. 토큰에 담을 정보 준비
//        Long userId = 1L;
//
//        String dataThatYouWannaPut = "레오는 도마뱀이야";
//
//        //3. 토큰 발급
//        String token = jwtUtils.createToken(userId);
//
//        return token;
//    }
//
//    /*
//    인증이 필요한 API
//    이 API는 로그인한 사용자만 접근할 수 있는 예제.
//    Authorization 헤더에서 JWT를 추출하여 사용자를 인증
//    주석을 활용해서 일반 토큰 Bearer 토큰을 Authorization 헤더에서 추출해서 활용
//     */
//    @GetMapping("/api")
//    public void authRequiredAPI(HttpServletRequest request) throws UnsupportedEncodingException {
//        //1. requestHeader 에서 토큰 추출
//        String token = request.getHeader("Authorization");
//
//        //2. 토큰에서 정보 추출
//        //2-1. 일반 토큰 사용할 경우
//        Long userId = jwtUtils.extractUserIdFromToken(token);
//
//        //2-2. 베어러 토큰 사용할 경우
////        Long userId = jwtUtils.extractUserIdFromBearerToken(token);
//
//        //3. 유저 아이디 활용
//        log.info("유저 아이디는 : {}", userId);
//
//
//    }
//
//}
