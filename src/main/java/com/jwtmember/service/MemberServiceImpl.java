package com.jwtmember.service;

import com.jwtmember.domain.Member;
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
}
