package com.jwtmember.jwt;

import com.jwtmember.domain.Authority;
import com.jwtmember.domain.Member;
import com.jwtmember.service.CustomUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    /**
     * HTTP 요청이 올 때마다 토큰을 검증한다.doFilterInternal 메서드에서 토큰의 유효성을 확인.
     */
    private final JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 요청에서 access토큰 헤더 가져오기
        String authorization = request.getHeader("Authorization");
        log.info(authorization);

        // 토큰이 없다면 다음 필터로 넘긴다.
        if(authorization == null || !authorization.startsWith("Bearer ")) {

            filterChain.doFilter(request, response); //권한이 필요 없는 요청도 있을수 있기 때문에 다음 필터로 넘겨야한다.
            return; //메서드 종료
        }

        // Bearer 공백 제거.
        String accessToken = authorization.split(" ")[1];


        // 토큰 만료 여부 체크, 확인 시 다음 필터로 넘기지않는다
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {

          response.getWriter().print("access token expired");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 토큰 카테고리 확인 (access 인지 체크 토큰 발급시 페이로드에 명시했다.)
        String category = jwtUtil.getCategory(accessToken);

        if(!category.equals("Authorization")) {

            response.getWriter().print("Invalid access token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // email, role 값을 꺼내와서 일시적인 세션 생성
        String userEmail = jwtUtil.getUserEmail(accessToken);
        String role = jwtUtil.getRole(accessToken);

        Member member = Member.authorization(userEmail, null, Authority.valueOf(role));
        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);


    }
}
