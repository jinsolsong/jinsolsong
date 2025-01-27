package com.spring.restartnewsfeed.common.login;

import com.spring.restartnewsfeed.common.auth.PasswordEncoder;
import com.spring.restartnewsfeed.common.jwt.JwtUtils;
import com.spring.restartnewsfeed.user.domain.User;
import com.spring.restartnewsfeed.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public JwtLoginResponseDto UserLoginService(
            JwtLoginRequestDto requestDto,
            HttpServletRequest servletRequest
    ) throws UnsupportedEncodingException {
        //1. requestDto 에서 데이터 받아오기
//        String email = requestDto.getEmail();
//        String password = requestDto.getPassword();

        //2. DB 에서 회원 조회하기
        User findUser = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));

        //3. 비밀번호 검증
        boolean matchesPassword = passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword());

        if (!matchesPassword) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }

//        //4. 세션 생성 and 조회
//        HttpSession session = servletRequest.getSession();
//        session.setAttribute("userId", findUser.getId());
//
//        //5. 세션 아이디 추출
//        String sessionId = session.getId();
        String token = jwtUtils.createToken(findUser.getId(), findUser.getEmail());


        //6. responseDto 만들기
        String message = "로그인 완료";
        return new JwtLoginResponseDto(token, message);

    }
}
