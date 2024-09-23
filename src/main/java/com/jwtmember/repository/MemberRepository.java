package com.jwtmember.repository;

import com.jwtmember.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {


    boolean existsByEmail(String email);
    boolean existsByNickName(String username);
}
