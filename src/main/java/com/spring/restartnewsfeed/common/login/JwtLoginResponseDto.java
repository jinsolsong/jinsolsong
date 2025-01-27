package com.spring.restartnewsfeed.common.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtLoginResponseDto {

    private String token;

    private String message;


}
