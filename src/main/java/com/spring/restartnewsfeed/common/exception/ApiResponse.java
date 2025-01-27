package com.spring.restartnewsfeed.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiResponse {

    //Api 응답에 들어올 필드
    private HttpStatus status;

    private String message;

    //반환타입이 ApiResponse 인 error 메서드
    public static ApiResponse error(HttpStatus status, String message) {
        return new ApiResponse(status, message);
    }


}
