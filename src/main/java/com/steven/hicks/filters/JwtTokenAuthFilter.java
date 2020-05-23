package com.steven.hicks.filters;


import com.steven.hicks.services.JwtTokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtTokenAuthFilter extends GenericFilterBean {

    JwtTokenService m_jwtTokenService;
    public JwtTokenAuthFilter(JwtTokenService provider) {
        this.m_jwtTokenService = provider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        try
        {
            String token = m_jwtTokenService.resolveToken((HttpServletRequest) req);
            if (m_jwtTokenService.validateToken(token)) {
                Authentication auth = m_jwtTokenService.getAuthentication(token);

                if (auth != null)
                    SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (UsernameNotFoundException e)
        {
//            ((HttpServletResponse)res).setStatus(404);
            throw new ServletException();
//            e.printStackTrace();
        }

        chain.doFilter(req, res);
    }
}
