package com.jwtmember.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@SuperBuilder // 부모클래스에서 정의한 메서드, 필드를 자식 클래스에서도 사용가능하다
public class ExceptionRes {

    private String title; // 에러 발생 원인
    private Integer status; // HttpStatus 표기
    private LocalDateTime timestamp; // 에러 발생 시간
    private String developMessage; // 에러가 발생한 class 명을 개발자에게 전달
}
