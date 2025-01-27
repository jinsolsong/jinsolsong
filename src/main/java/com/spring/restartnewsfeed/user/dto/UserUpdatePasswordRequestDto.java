package com.spring.restartnewsfeed.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdatePasswordRequestDto {

    @Size(min = 8, max = 12)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>])[a-zA-Z0-9!@#$%^&*(),.?\":{}|<>]+$",
            message = "영문 대소문자, 숫자, 특수문자를 각각 최소 1글자씩 포함해야 합니다.")
    private String oldPassword;

    @Size(min = 8, max = 12)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>])[a-zA-Z0-9!@#$%^&*(),.?\":{}|<>]+$",
            message = "영문 대소문자, 숫자, 특수문자를 각각 최소 1글자씩 포함해야 합니다.")
    private String newPassword;

}
