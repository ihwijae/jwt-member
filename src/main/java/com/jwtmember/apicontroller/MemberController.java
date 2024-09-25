package com.jwtmember.apicontroller;

import com.jwtmember.service.MemberFindAllResponse;
import com.jwtmember.service.MemberService;
import com.jwtmember.service.MemberSignUpRequest;
import com.jwtmember.service.MemberSignUpResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class MemberController {

    private final MemberService memberService;




    @PostMapping("/members")
    @ResponseStatus(HttpStatus.OK)
    public MemberSignUpResponse signUp(@RequestBody @Valid MemberSignUpRequest request) {
        log.info("회원가입 시작");
        MemberSignUpResponse memberSignUpRsp = memberService.singUp(request);
        return memberSignUpRsp;
    }


    // 이메일 중복 검증 api
    @GetMapping("/members/email/{email}")
    public String emailDuplicate(@PathVariable String email) {
        memberService.emailDuplicate(email);
        return "이메일 사용이 가능합니다";
    }

    // 닉네임 중복 검증 api
    @GetMapping("/members/nickName/{nickName}")
    public String nickNameDuplicate(@PathVariable String nickName) {
        memberService.nickNameDuplicate(nickName);
        return "닉네임 사용이 가능합니다";
    }

    @GetMapping("/members")
    public List<MemberFindAllResponse> memberFindAll() {
        return memberService.memberFindAll();
    }




}
