package com.jwtmember.jwt;

import com.jwtmember.domain.Authority;
import com.jwtmember.domain.Member;
import com.jwtmember.service.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    /**
     * HTTP 요청이 올 때마다 토큰을 검증한다.doFilterInternal 메서드에서 토큰의 유효성을 확인.
     */
    private final JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 요청에서 Authorization 헤더 가져오기
        String authorization = request.getHeader("Authorization");

        // Authorization 헤더 검증
        if(authorization == null || !authorization.startsWith("Bearer ")) {

            log.error("jwt 토큰 없음");
            filterChain.doFilter(request, response);
            return;
        }

        //토큰 소멸시간 검증
        String token = authorization.split(" ")[1];
        //["Bearer", "abcdef123456"] 이런식의 배열 생성 , 띄어쓰기 구문으로 부터 앞부분, 뒷부분을 가지는 두 개의 인덱스를 가지는 List 중에 2번째인 1번인덱스인 토큰.

        if(jwtUtil.isExpired(token)) {

            log.error("토큰 만료시간 지남");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰에서 email, role 획득
        String userEmail = jwtUtil.getUserEmail(token);
        String role = jwtUtil.getRole(token);

        // Member Entity 생성하여 값 set
        Member member = Member.authorization(userEmail, "extemplepassword", Authority.valueOf(role));

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        //스프링 시큐리티 인증토큰 생성
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        //세션에 사용자 등록 (스프링 시큐리티가 관리하는 컨텍스트에 넣는다.)
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);


    }
}
