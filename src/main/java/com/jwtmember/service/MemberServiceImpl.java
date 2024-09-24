package com.jwtmember.service;

import com.jwtmember.domain.Member;
import com.jwtmember.exception.MemberException;
import com.jwtmember.repository.MemberQueryRepository;
import com.jwtmember.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;

    @Override
    public MemberSignUpResponse singUp(MemberSignUpRequest req) {

        Member entity = Member.toEntity(req);
        Member save = memberRepository.save(entity);

        return MemberSignUpResponse.toDto(save);
    }

    @Override
    public String emailDuplicate(String email) {
        boolean b = memberRepository.existsByEmail(email);

        String result;
        if(!b) {
            result = "이메일 사용이 가능합니다.";
            return result;
        } else {
            throw new MemberException.EmailDuplicateException("이메일이 중복 됐습니다");
        }

    }

    @Override
    public String nickNameDuplicate(String nickName) {
        boolean b = memberRepository.existsByNickName(nickName);

        String result;

        if(!b) {
            result = "닉네임 사용이 가능합니다.";
            return result;
        } else {
            throw new MemberException.NickNameDuplicateException("이미 사용중인 닉네임 입니다 다른 닉네임을 선택하세요.");
        }

    }
}
