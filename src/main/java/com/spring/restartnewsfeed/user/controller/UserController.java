package com.spring.restartnewsfeed.user.controller;

import com.spring.restartnewsfeed.user.dto.UserRequestDto;
import com.spring.restartnewsfeed.user.dto.UserResponseDto;
import com.spring.restartnewsfeed.user.dto.UserUpdatePasswordRequestDto;
import com.spring.restartnewsfeed.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 유저 생성기기능
    /*
    알아서 확인해보세요 확인하는법 알려줬잖아요

     */

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> createUser(
            @Valid @RequestBody UserRequestDto requestDto
    ) {
        UserResponseDto response = userService.createUser(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //유저 단건 조회기능
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long userId) {
        UserResponseDto responseDto = userService.findUserByIdService(userId);
        return new ResponseEntity<>(responseDto, HttpStatus.FOUND);
    }

    //유저 목록 조회기능
    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> findAllUsers() {
        List<UserResponseDto> responseDto = userService.findAllUsersService();
        return new ResponseEntity<>(responseDto, HttpStatus.FOUND);
    }

    //유저 정보(비밀번호) 수정기능
    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUserPassword(
            @PathVariable Long userId,
            @Valid
            @RequestBody UserUpdatePasswordRequestDto requestDto,
            HttpServletRequest servletRequest
    ) {
        Long tokenUserId = (Long) servletRequest.getAttribute("userId");

        if (!tokenUserId.equals(userId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        userService.updateUserPasswordService(userId, requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //유저 삭제 기능(소프트)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long userId,
            HttpServletRequest servletRequest,
            @RequestAttribute("userId") Long tokenUserId // 이렇게도 할 수 있음.
    ) {

//        Long tokenUserId = (Long) servletRequest.getAttribute("userId");

        if (!tokenUserId.equals(userId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        userService.deleteUserService(userId);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findUserByString(@RequestParam int age) {
        List<UserResponseDto> responseDto = userService.findUserByName(age);
        return new ResponseEntity<>(responseDto, HttpStatus.FOUND);
    }


}