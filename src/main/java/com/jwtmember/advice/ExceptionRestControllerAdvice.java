package com.jwtmember.advice;

import com.jwtmember.domain.Member;
import com.jwtmember.exception.MemberException;
import com.jwtmember.exception.RefreshTokenException;
import com.jwtmember.exception.RefreshTokenException.RefreshTokenDataBase;
import com.jwtmember.exception.RefreshTokenException.RefreshTokenExpiredException;
import com.jwtmember.service.MemberSignUpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ExceptionRestControllerAdvice {

    // @Vaild 검증
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();


        // {필드이름 : 오류 메시지} 출력
        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
        { errors.put(error.getField(), error.getDefaultMessage());}
        );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberException.EmailDuplicateException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateEmailException(MemberException.EmailDuplicateException ex) {
        Map<String, String> errors = new HashMap<>();

        String message = ex.getMessage();
        errors.put("email", message);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberException.NickNameDuplicateException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateNickNameException(MemberException.NickNameDuplicateException ex) {
        Map<String, String> errors = new HashMap<>();


        String message = ex.getMessage();
        errors.put("nickname", message);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberException.CheckedPassWordDuplicateException.class)
    public ResponseEntity<List<String>> handleDuplicateCheckedPassWordException(MemberException.CheckedPassWordDuplicateException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberException.PassWordDuplicateException.class)
    public ResponseEntity<List<String>> handleDuplicatePassWordException(MemberException.PassWordDuplicateException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(RefreshTokenException.RefreshTokenNullException.class)
    public ResponseEntity<List<String>> handleRefreshTokenException(RefreshTokenException.RefreshTokenNullException ex) {

        return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<List<String>> handleRefreshTokenExpiredException(RefreshTokenExpiredException ex) {

        return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RefreshTokenDataBase.class)
    public ResponseEntity<List<String>> handleRefreshTokenDataBase(RefreshTokenDataBase ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }



    private ResponseEntity<List<String>> createErrorResponse(String message, HttpStatus status) {
        List<String> errors = new ArrayList<>();

        errors.add(message);

        return new ResponseEntity<>(errors, status);
    }



}
