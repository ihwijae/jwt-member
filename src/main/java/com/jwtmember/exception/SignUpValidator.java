//package com.jwtmember.exception;
//
//import com.jwtmember.repository.MemberRepository;
//import com.jwtmember.service.MemberSignUpReq;
//import lombok.RequiredArgsConstructor;
//import org.hibernate.annotations.Comment;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Component
//@RequiredArgsConstructor
//public class SignUpValidator implements Validator {
//
//    private final MemberRepository memberRepository;
//
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//    return MemberSignUpReq.class.isAssignableFrom(clazz);
//    // 요청으로 들어온 MemberSignUpReq가 == class 클래스 인지
//    // 만약 MemberSignUpReq 의 자식 클래스가 있다면 자식 클래스도 검증을 통과 한다 MemberSignUpReq == 자식클래스
//        // == 비교보다 isAssignableFrom 를 사용하자 자식 클래스까지 커버가 되니까.
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        MemberSignUpReq req = (MemberSignUpReq) target;
//            if(memberRepository.existsByEmail(req.getEmail())
//            ) {
//                errors.reject(req.getEmail(), "이미 사용중인 이메일 입니다.");
//            }
//            if(memberRepository.existsByNickName(req.getNickname())) {
//                errors.reject(req.getNickname(), "이미 사용중인 닉네임 입니다.");
//            }
//    }
//}
