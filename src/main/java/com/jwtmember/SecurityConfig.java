package com.jwtmember;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwtmember.jwt.JwtFilter;
import com.jwtmember.jwt.JwtUtil;
import com.jwtmember.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final AuthenticationConfiguration authenticationConfiguration;
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;


    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //csrf disable
        http
                .csrf((auth) -> auth.disable());

        //From 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

        //http basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());

        //경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/login", "/", "/api/join").permitAll() // 모든 권한을 허용
                        .requestMatchers("/admin").hasRole("ADMIN") // ADMIN 권한 사용자만 접근
                        .anyRequest().authenticated()); // 다른 요청에 대해서는 로그인한 사용자만 접근 가능

        //세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // session을 무상태로 설정하는것 (스테이트리스)


        // LoginFilter (필터) 추가 - 스프링 시큐리티 필터체인에 필터를 등록
        http
                .addFilterAt(new LoginFilter(authenticationManagerBean(authenticationConfiguration), objectMapper, jwtUtil), UsernamePasswordAuthenticationFilter.class);

        //JwtFilter 필터 등록
        http.addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class); //로그인 필터 앞에 먼저 실행한다는 뜻.


        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




}
