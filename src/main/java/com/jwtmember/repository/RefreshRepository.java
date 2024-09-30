package com.jwtmember.repository;

import com.jwtmember.domain.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


public interface RefreshRepository extends JpaRepository<Refresh, Long> {

    Boolean existsByRefreshToken(String refreshToken);


    @Transactional
    void deleteByRefreshToken(String refreshToken);
}
