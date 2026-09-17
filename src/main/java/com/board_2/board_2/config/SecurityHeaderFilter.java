package com.board_2.board_2.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityHeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {

        // 클릭재킹 방지 — 이 페이지를 다른 사이트의 <iframe> 안에 못 넣게 막음
        response.setHeader("X-Frame-Options", "DENY");

        // 브라우저가 파일 내용을 "추측"해서 다른 타입으로 해석하는 것을 막음
        // (예: 텍스트 파일을 실행 가능한 스크립트로 오인하는 공격 방지)
        response.setHeader("X-Content-Type-Options", "nosniff");

        // 다른 사이트로 이동할 때 이전 페이지의 상세 URL이 노출되는 걸 최소화
        response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");

        // 콘텐츠 보안 정책 — 어떤 출처의 리소스만 허용할지 명시
        response.setHeader("Content-Security-Policy",
                "default-src 'self'; "
              + "script-src 'self' 'unsafe-inline'; "
              + "style-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net; "
              + "font-src 'self' https://cdn.jsdelivr.net; "
              + "img-src 'self' data:; "
              + "frame-ancestors 'none';");

        filterChain.doFilter(request, response);
    }
}