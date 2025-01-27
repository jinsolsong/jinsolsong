package com.spring.restartnewsfeed.common.aop;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//특정 어노테이션이 메서드에만 적용되도록 제한
@Target(ElementType.METHOD)
//어노테이션이 어느 시점까지 유지될건지 지정
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackTime {


}
