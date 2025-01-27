package com.spring.restartnewsfeed.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRequestDto {

    private final String name;

    @NotBlank     // Character Sequence Allow
    // https://docs.jboss.org/hibernate/validator/8.0/reference/en-US/html_single/#section-builtin-constraints
    private final String email;

    @Size(min = 8, max = 12)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>])[a-zA-Z0-9!@#$%^&*(),.?\":{}|<>]+$",
            message = "영문 대소문자, 숫자, 특수문자를 각각 최소 1글자씩 포함해야 합니다.")
    private final String password;

    // 인트타입은 size 로 하면 오류발생
//    @Min(1)
//    @Max(5)
//    private int point;

//    @NotNull
//    private Long requiredField;
}
