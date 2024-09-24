package com.jwtmember.repository;

import com.jwtmember.domain.Member;
import jakarta.persistence.Column;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;


    @Test
    public void saveTest () {
        Member member = new Member();
        member.setEmail("test@example.com");

        memberRepository.save(member);

        memberRepository.existsByEmail("test@example.com");
    }

}