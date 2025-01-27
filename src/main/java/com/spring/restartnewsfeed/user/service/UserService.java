package com.spring.restartnewsfeed.user.service;

import com.spring.restartnewsfeed.common.aop.TrackTime;
import com.spring.restartnewsfeed.common.auth.PasswordEncoder;
import com.spring.restartnewsfeed.common.exception.user.AlreadyUsedException;
import com.spring.restartnewsfeed.common.exception.user.NotFoundException;
import com.spring.restartnewsfeed.user.domain.User;
import com.spring.restartnewsfeed.user.dto.UserRequestDto;
import com.spring.restartnewsfeed.user.dto.UserResponseDto;
import com.spring.restartnewsfeed.user.dto.UserUpdatePasswordRequestDto;
import com.spring.restartnewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public UserResponseDto createUser(UserRequestDto requestDto) {

        //만약에 사용자가 입력한 이메일이 이미 생성된 이메일 이라면
        //생성이 불가능하고
        //그게아니면, 생성이 가능함.
        Optional<User> findUser = userRepository.findByEmail(requestDto.getEmail());

        if (findUser.isPresent()) {
            User user = findUser.get();
            if (user.isDeleted()) {
                throw new AlreadyUsedException("이미 탈퇴한 아이디입니다");
            }

            throw new AlreadyUsedException("이미 사용중인 아이디입니다");
        }


        // DB에 존재하는지 안하는지 boolean 타입으로 확인
//        boolean isExistUser = userRepository.existsByEmail(requestDto.getEmail());
//
//        if (isExistUser) {
//            throw new RuntimeException("이미 가입된 이메일입니다.");
//        }

        //중복이아니면 가입시켜주기
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(requestDto.getName(), requestDto.getEmail(), encodedPassword);

        User saveUser = userRepository.save(user);

        return UserResponseDto.toDto(saveUser);
    }

    public UserResponseDto findUserByIdService(Long id) {
        //레포지토리에서 유저 조회
        //유저가 없다면 익셉션
        //있다면 반환
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다. 아이디를 확인해주세요"));

        return UserResponseDto.toDto(user);
    }

    public List<UserResponseDto> findAllUsersService() {
        //레포지토리에서 유저"들"조회
        List<User> findAll = userRepository.findAll();
        //조회한 유저 담기
        List<UserResponseDto> result = new ArrayList<>();
        //가지고온 데이터랑 반환타입이 다르니 맞춰주기(for문)
        for (User user : findAll) {
            UserResponseDto userResponseDto = UserResponseDto.toDto(user);
            result.add(userResponseDto);
        }
        //반환
        return result;
    }

    @Transactional
    public void updateUserPasswordService(Long id, UserUpdatePasswordRequestDto requestDto) {

        //레포지토리에서 유저 조회 -> 유저가 없다면 익셉션
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다. 아이디를 확인해주세요"));
        //유저가 조회되었다면

        // TODO 엔티티에 위임해보기
        //2. 조회된 유저의 패스워드가 입력받은값(oldPassword)와 같은지 확인 같지 않다면 익셉션
        if (!passwordEncoder.matches(requestDto.getOldPassword(), findUser.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }
        //3. 유저가 이전의 비밀번호와 같은 비밀번호를 사용하려는지 확인 -> 그렇다면 익셉션
        if (passwordEncoder.matches(requestDto.getNewPassword(), findUser.getPassword())) {
            throw new RuntimeException("같은 비밀번호를 사용할 수 없습니다");
        }

        //1. 입력받을 새 비밀번호에 대한 인코딩
        String encodePassword = passwordEncoder.encode(requestDto.getNewPassword());

        //4. 같은 비밀번호를 사용하지 않는다면 새비밀번호를 인코딩 후 저장
        findUser.updatePassword(encodePassword);
    }

    @Transactional
    public void deleteUserService(Long id) {
        //저장된 유저 검색 -> 없으면 익셉션
        //찾았다면 유저의 상태(isDeleted)를 true 로 변경 후 저장
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다. 아이디를 확인해주세요"));

        findUser.updateStatus();
    }

    @TrackTime
    public List<UserResponseDto> findUserByName(int age) {

        List<User> usersByNameContaining = userRepository.findUsersByAge(age);
        //조회한 유저 담기
        List<UserResponseDto> result = new ArrayList<>();
        //가지고온 데이터랑 반환타입이 다르니 맞춰주기(for문)
        for (User user : usersByNameContaining) {
            UserResponseDto userResponseDto = UserResponseDto.toDto(user);
            result.add(userResponseDto);
        }
        //반환
        return result;

    }
}
