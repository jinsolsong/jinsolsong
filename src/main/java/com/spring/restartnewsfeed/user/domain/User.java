package com.spring.restartnewsfeed.user.domain;

import com.spring.restartnewsfeed.user.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user", indexes = @Index(name = "idx_user_age", columnList = "age"))
@NoArgsConstructor
@Getter

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
//    @SequenceGenerator(
//            name = "USER_SEQ_GENERATOR",
//            sequenceName = "USER_SEQ",
//            initialValue = 1,
//            allocationSize = 50
//    )
    private Long id;

    private String name;

    @Column(nullable = false)
    private String email;

    private String password;

    private boolean isDeleted;

    private int age;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }


    public static User of(UserRequestDto requestDto) {
        return new User(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );
    }

    public void updateStatus() {
        this.isDeleted = !this.isDeleted;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

}
