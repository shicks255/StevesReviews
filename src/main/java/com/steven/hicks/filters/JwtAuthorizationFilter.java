package com.steven.hicks.filters;

import com.steven.hicks.services.JwtTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtTokenService m_jwtTokenService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
        super(authenticationManager);
        this.m_jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        try
        {
            String token = m_jwtTokenService.resolveToken((HttpServletRequest) request);
            if (m_jwtTokenService.validateToken(token)) {
                Authentication auth = m_jwtTokenService.getAuthentication(token);
                if (auth != null)
                    SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                SecurityContextHolder.clearContext();
            }
        } catch (Exception e)
        {
//            ((HttpServletResponse)res).setStatus(404);
            throw new ServletException();
//            e.printStackTrace();
        }

        chain.doFilter(request, response);
    }
}
