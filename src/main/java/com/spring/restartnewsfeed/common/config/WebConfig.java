package com.spring.restartnewsfeed.common.config;

import com.spring.restartnewsfeed.common.aop.AspectPractice;
import com.spring.restartnewsfeed.common.jwt.JwtUtils;
import com.spring.restartnewsfeed.common.login.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//스프링이랑 필터랑 연결해주는 역할 // 필터를 만들때는 꼭 만들어줘야한다.
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtUtils jwtUtils;

    @Bean
    public FilterRegistrationBean jwtLoginFilter() {
        FilterRegistrationBean<JwtFilter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new JwtFilter(jwtUtils));

        filterRegistrationBean.setOrder(1);

        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

    //AspectPractice 클래스의 객체를 빈으로 등록
    @Bean
    public AspectPractice aspectPractice() {
        return new AspectPractice();
    }

}
