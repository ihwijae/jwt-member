package com.jwtmember.apicontroller;

import com.jwtmember.service.MemberService;
import com.jwtmember.service.MemberSignUpReq;
import com.jwtmember.service.MemberSignUpRsp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/members")
    @ResponseStatus(HttpStatus.OK)
    public MemberSignUpRsp signUp(@RequestBody @Valid MemberSignUpReq req) {
        log.info("회원가입 시작");
        MemberSignUpRsp memberSignUpRsp = memberService.singUp(req);
        return memberSignUpRsp;
    }
}
