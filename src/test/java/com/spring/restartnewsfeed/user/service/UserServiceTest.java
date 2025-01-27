package com.spring.restartnewsfeed.user.service;

import com.spring.restartnewsfeed.common.auth.PasswordEncoder;
import com.spring.restartnewsfeed.common.exception.user.AlreadyUsedException;
import com.spring.restartnewsfeed.user.domain.User;
import com.spring.restartnewsfeed.user.dto.UserRequestDto;
import com.spring.restartnewsfeed.user.dto.UserResponseDto;
import com.spring.restartnewsfeed.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.spring.restartnewsfeed.mock.MockData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Transactional
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    /**
     * 테스트 교정
     * <p>
     * DB 쪽으로 들어가고싶으심
     */
    @Test
    @DisplayName("유저 생성 성공")
    public void createUser_success() {
        UserRequestDto requestDto = new UserRequestDto(TEST_NAME1, TEST_EMAIL1, TEST_PASSWORD1);

        when(userRepository.findByEmail(TEST_EMAIL1)).thenReturn(Optional.empty());

        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn(encodedPassword);

        User user = new User(requestDto.getName(), requestDto.getEmail(), encodedPassword);
        ReflectionTestUtils.setField(user, "id", 1L);

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDto userResponseDto = userService.createUser(requestDto);

        assertThat(userResponseDto).isNotNull();
        assertThat(userResponseDto.getName()).isEqualTo(TEST_NAME1);
        assertThat(userResponseDto.getEmail()).isEqualTo(TEST_EMAIL1);

    }

    @Test
    @DisplayName("유저 생성 실패 - 이미 가입된 이메일")
    public void createUser_fail_email_already_subscribed() {

        UserRequestDto requestDto = new UserRequestDto(TEST_NAME1, TEST_EMAIL1, TEST_PASSWORD1);

        when(userRepository.findByEmail(TEST_EMAIL1)).thenReturn(Optional.of(TEST_USER1));

        assertThrows(AlreadyUsedException.class, () -> {
            userService.createUser(requestDto); // 실제 유저 생성 메서드를 호출
        });

    }

    @Test
    @DisplayName("유저 생성 실패 - 이미 탈퇴한 이메일")
    public void createUser_fail_withdrawn_email() {

        UserRequestDto requestDto = new UserRequestDto(TEST_NAME1, TEST_EMAIL1, TEST_PASSWORD1);
        User existingUser = mock(User.class);

        when(userRepository.findByEmail(TEST_EMAIL1)).thenReturn(Optional.of(existingUser));
        when(existingUser.isDeleted()).thenReturn(true);

        assertThrows(AlreadyUsedException.class, () -> {
            userService.createUser(requestDto); // 실제 유저 생성 메서드를 호출
        });
    }

}