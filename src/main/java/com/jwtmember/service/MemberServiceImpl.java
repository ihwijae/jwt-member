package com.jwtmember.service;

import com.jwtmember.domain.Member;
import com.jwtmember.exception.MemberException;
import com.jwtmember.repository.MemberQueryRepository;
import com.jwtmember.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;

    @Override
    @Transactional
    public MemberSignUpResponse singUp(MemberSignUpRequest req) {

       // 이메일 중복 검증
        emailDuplicate(req.getEmail());

        // 닉네임 중복 검증
        nickNameDuplicate(req.getNickname());

        Member entity = Member.toEntity(req);
        Member save = memberRepository.save(entity);

        return MemberSignUpResponse.toDto(save);
    }

    @Override
    public void emailDuplicate(String email) {
        boolean result = memberRepository.existsByEmail(email);

        if(result) {
            throw new MemberException.EmailDuplicateException("중복된 이메일 입니다.");
        }

    }

    @Override
    public void nickNameDuplicate(String nickName) {
        boolean result = memberRepository.existsByNickName(nickName);

        if(result) {
            throw new MemberException.NickNameDuplicateException("중복된 닉네임 입니다 다른 닉네임을 선택하세요");
        }
    }
}
