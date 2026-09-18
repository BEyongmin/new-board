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

        response.setHeader("X-Frame-Options", "DENY");

        response.setHeader("X-Content-Type-Options", "nosniff");

        response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
        
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