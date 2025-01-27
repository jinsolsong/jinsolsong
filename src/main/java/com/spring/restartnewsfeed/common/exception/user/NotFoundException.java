package com.spring.restartnewsfeed.common.exception.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotFoundException extends RuntimeException {

    private String errorMessage;

}
