package com.spring.restartnewsfeed.user.repository;

import com.spring.restartnewsfeed.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.spring.restartnewsfeed.mock.MockData.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
//테스트코드에서 transactional은 롤백도 됨
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저 저장 성공")
    public void saveUser_success() {
        User user = TEST_USER1;

        User saveUser = userRepository.save(user);

        assertThat(saveUser).isNotNull();
        assertThat(saveUser.getName()).isEqualTo(TEST_NAME1);
    }

    @Test
    @DisplayName("유저 이메일로 찾기 성공")
    public void findByEmail_success() {
        User user = TEST_USER1;

        userRepository.save(user);

        Optional<User> findUser = userRepository.findByEmail(TEST_EMAIL1);

        assertThat(findUser).isNotEmpty();
    }

    @Test
    @DisplayName("유저 이메일로 찾기 실패")
    public void findByEmail_fail() {
        User user = TEST_USER1;

        userRepository.save(user);

        Optional<User> findUser = userRepository.findByEmail(TEST_EMAIL2);

        assertThat(findUser).isEmpty();
    }


    @Test
    @DisplayName("유저 이메일 존재 여부 확인 성공")
    public void existsByEmail_success() {
        User user = TEST_USER1;

        userRepository.save(user);

        boolean exists = userRepository.existsByEmail(TEST_EMAIL1);

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("유저 이메일 존재 여부 확인 실패")
    public void existsByEmail_fail() {
        User user = TEST_USER1;

        userRepository.save(user);

        boolean exists = userRepository.existsByEmail(TEST_EMAIL2);

        assertThat(exists).isFalse();
    }


}