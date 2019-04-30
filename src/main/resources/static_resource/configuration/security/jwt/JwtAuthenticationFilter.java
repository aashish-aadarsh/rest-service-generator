package com.devop.aashish.java.myapplication.configuration.security.jwt;

import com.devop.aashish.java.myapplication.application.exception.UnAuthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException, IOException {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        try {
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                String userId = jwtTokenProvider.getUserIdFromJWT(token);
                UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) jwtTokenProvider.getAuthentication(userId);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (UnAuthenticatedException ex) {
            SecurityContextHolder.clearContext();
            httpServletResponse.sendError(ex.getStatus(), ex.getMessage());
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
