package com.jwtmember.apicontroller;

import com.jwtmember.service.MemberService;
import com.jwtmember.service.MemberSignUpRequest;
import com.jwtmember.service.MemberSignUpResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    // API 예외처리 테스트
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse SingUpDuplication(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        return new ErrorResponse("중복입니다", "어떤게중복");
    }
    

    @PostMapping("/members")
    @ResponseStatus(HttpStatus.OK)
    public MemberSignUpResponse signUp(@RequestBody @Valid MemberSignUpRequest request) {
        log.info("회원가입 시작");

        MemberSignUpResponse memberSignUpRsp = memberService.singUp(request);
        return memberSignUpRsp;
    }
}
