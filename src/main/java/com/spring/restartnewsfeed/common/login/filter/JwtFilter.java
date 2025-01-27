package com.spring.restartnewsfeed.common.login.filter;

import com.spring.restartnewsfeed.common.jwt.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final JwtUtils jwtUtils;
    //JWT 토큰 검증을 거치치 않을 URI
    private static final String[] WHITE_LIST = {"/users/signup", "/login"};

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        //만약에 isWhiteList 에 있는 URI 가 아니라면
        if (!isWhiteList(requestURI)) {

            //Authorization 헤더에서 JWT 토큰을 추출해줘
            String jwt = httpRequest.getHeader("Authorization");

            //만약에 JWT 토큰이 없거나, 비어있다면
            if (jwt.isBlank() || jwt == null) {
                //BAD_REQUEST 를 반환하고 종료해줘.
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "토큰이 필요합니다");
                return;
            }
            //JWT 토큰에서 사용자의 ID를 추출하고,
            try {
                Long userId = jwtUtils.extractUserIdFromToken(jwt);
                //정보를 담기
                httpRequest.setAttribute("userId", userId);

//                if (requestURI.startsWith("/users/")) {
//                    String[] uriParts = requestURI.split("/");
//
//                    Long userIdFrom = Long.valueOf(uriParts[2]);
//
//                    if (!userId.equals(userIdFrom)) {
//                        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "사용자 아이디가 일치하지 않습니다");
//                        return;
//                    }
//                }

                //토큰이 유효하지 않다면 UNAUTHORIZED 를 반환하고 종료해줘
            } catch (Exception e) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다");
                return;
            }

        }
        // 모든것이 통과 되었다면, 다음으로 넘어가기
        chain.doFilter(request, response);
    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}

/**
 * JWT
 * 유저생성 > 로그인 > 토큰발급 >그 토큰 사용
 * <p>
 * session
 * 유저생성 > 로그인 > 세션발급?
 * <p>
 * 생성 > 로그인 > 세션만들기 > 쿠키에 담기 > 클라이언트한테 주기 > 클라이언트가 요청할때 헤더에 세션아이디를 담아서 줌 >
 * <p>
 * 지금은 유저만있어서 괜찮음.
 * <p>
 * 하지만 포스트나, 스토어 같은 뭔가가 생기면
 * 컨트롤러에서 하는게 좋음.
 */
