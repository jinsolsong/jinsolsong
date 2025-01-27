package com.spring.restartnewsfeed.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Aspect
public class AspectPractice {

    //어노테이션 기반 포인트 컷
    @Pointcut("@annotation(com.spring.restartnewsfeed.common.aop.TrackTime)")
    private void trackTimeAnnotation() {
    }

    /**
     * 여기서 트랜잭셔널을 해보면...........................?, 생각해보기
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("trackTimeAnnotation()")
    public Object adviceAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {

        Long startTime = System.currentTimeMillis();
        try {
//            log.info("Start : {}", joinPoint.getSignature().getName());


            Object result = joinPoint.proceed();

//            log.info("userCreateTime : {} ", createdDate());

//            //joinPoint.getSignature 는 메서드의 이름, 반환타입, 매개변수 타입등이 포함됨.
//            //메서드 이름만 출력
//            log.info("result : {} ", joinPoint.getSignature().getName());
//            //메서드의 위치 출력(패키지 위치까지)
//            log.info("result : {} ", joinPoint.getSignature().getDeclaringType());
//            //메서드의 반환타입 , 메서드 이름, 위치 매개변수 출력
//            log.info("result : {} ", joinPoint.getSignature().toString());
//            //메서드의 위치 출력
//            log.info("result : {} ", joinPoint.getSignature().getDeclaringTypeName());
//            //.. 뭐지이건(메서드의 접근제어자 출력)
//            log.info("result : {} ", joinPoint.getSignature().getModifiers());
//            //메서드의 이름에 관련된 내용과 매개변수 세부정보(위치)까지 출력
//            log.info("result : {} ", joinPoint.getSignature().toLongString());
//            //메서드의 위치를 짧게 표시(어느클래스에 속해있는 무슨메서드이다 정도)
//            log.info("result : {} ", joinPoint.getSignature().toShortString());

            return result;

        } catch (Throwable e) {

            throw e;

        } finally {
            Long endTime = System.currentTimeMillis();
            Long executeTime = endTime - startTime;
            log.info("executeTime : {} ms ", executeTime);
//            log.info("End : {}", joinPoint.getSignature().getName());

        }

    }


    private String createdDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    /**
     * .........이름... 무슨어드바이즈인지 이름 다시
     * 직관적으로.......
     *
     * DTO 를 로깅.......
     */
}
