package com.jwtmember.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwtmember.service.CustomUserDetails;
import com.jwtmember.service.LoginRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;


@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;


    public LoginFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/login");
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        LoginRequest loginRequest = new LoginRequest();
        try {
            ServletInputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
             loginRequest = objectMapper.readValue(messageBody, LoginRequest.class);

        } catch (IOException e) {
//            throw new RuntimeException(e);

        }


        //클라이언트 요청에서 email, password 추출
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        log.info("email = {}, password = {}", email, password);


        //스프링 시큐리티에서 email, password 검증
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

        //토큰에 담은 값을 검증 하기위한 AuthenticationManager (검증 방법: DB에서 회원 정보 조회 후 UserDetailsService를 통해 유저 정보를 받고 검증 진행)
        return authenticationManager.authenticate(authToken);

    }


    // 로그인 검증 성공시 실행 하는 메서드 (여기서 JWT 토큰 발급)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

        log.info("로그인 성공");
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String email = customUserDetails.getUsername();


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();
        log.info("role = {}", role);

        String jwt = jwtUtil.createJwt(email, role, 60 * 60 * 10 * 1000L);

        response.addHeader("Authorization", "Bearer " + jwt);

    }


    // 로그인 검증 실패시 실행하는 메서드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

    log.info("로그인 실패");
    response.setStatus(401);
    }

}
