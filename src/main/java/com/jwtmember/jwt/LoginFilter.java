package com.jwtmember.jwt;

import com.jwtmember.domain.Authority;
import com.jwtmember.service.LoginRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;


@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public LoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/login");
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {


        //클라이언트 요청에서 email, password 추출
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        log.info("email = {}, password = {}", username, password);


        //스프링 시큐리티에서 email, password 검증
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        //토큰에 담은 값을 검증 하기위한 AuthenticationManager (검증 방법: DB에서 회원 정보 조회 후 UserDetailsService를 통해 유저 정보를 받고 검증 진행)
        return authenticationManager.authenticate(authToken);

    }


    // 로그인 검증 성공시 실행 하는 메서드 (여기서 JWT 토큰 발급)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        log.info("successful authentication");
    }


    // 로그인 검증 실패시 실행하는 메서드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

    log.info("unsuccessful authentication");
    }

}
