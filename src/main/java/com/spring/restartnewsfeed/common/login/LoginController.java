package com.spring.restartnewsfeed.common.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    //로그인API
    @PostMapping
    public ResponseEntity<JwtLoginResponseDto> loginUserAPI(
            @RequestBody JwtLoginRequestDto requestDto,
            HttpServletRequest servletRequest
    ) throws UnsupportedEncodingException {

        JwtLoginResponseDto userLoginResponseDto = loginService.UserLoginService(requestDto, servletRequest);

        return new ResponseEntity<>(userLoginResponseDto, HttpStatus.OK);

    }

    //비공개 API 인증된 유저에게만 줘야함
    @GetMapping("/private")
    public String privateAPI(HttpServletRequest request) {
        //1. 세션 추출 //세션을 만들지는 않고 가지고 오기만 함
        HttpSession session = request.getSession(false);

        //2. 세션이 존재하는지 확인
        if (session == null) {
            throw new RuntimeException("세션이 존재하지 않습니다");
        }

        //3. 세션이 존재하면 회원 데이터 추출
        Long userId = (Long) session.getAttribute("userId");
        String email = (String) session.getAttribute("email");

        return "접근성공";

    }

}
