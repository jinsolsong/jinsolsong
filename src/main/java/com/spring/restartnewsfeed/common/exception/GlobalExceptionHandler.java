package com.spring.restartnewsfeed.common.exception;

import com.spring.restartnewsfeed.common.exception.user.AlreadyUsedException;
import com.spring.restartnewsfeed.common.exception.user.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFoundException(NotFoundException notFoundException) {
        ApiResponse errorResponse = ApiResponse.error(HttpStatus.NOT_FOUND, notFoundException.getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyUsedException.class)
    public ResponseEntity<ApiResponse> handleAlreadyUsedException(AlreadyUsedException alreadyUsedException) {
        ApiResponse errorResponse = ApiResponse.error(HttpStatus.BAD_REQUEST, alreadyUsedException.getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
