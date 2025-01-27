package com.spring.restartnewsfeed.user.dto;

import com.spring.restartnewsfeed.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserResponseDto {

    private final Long id;

    private final String name;

    private final String email;

    private final int age;

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge()
        );
    }
}
