package com.jwtmember.service;

import com.jwtmember.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.jwtmember.exception.MemberException.*;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            return memberRepository.findByEmail(username)
                .map(member -> new CustomUserDetails(member))
                .orElseThrow(() -> new EmailDuplicateException("이메일이 존재하지 않습니다"));
    }
}
