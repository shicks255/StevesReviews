package com.steven.hicks.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steven.hicks.models.User;
import com.steven.hicks.models.dtos.UserLogin;
import com.steven.hicks.services.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Component
public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {

    JwtTokenService tokenService;

    private String jsonUsername = "";
    private String jsonPassword = "";

    Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    public JwtAuthFilter(AuthenticationManager authenticationManager, JwtTokenService tokenService) {
        this.tokenService = tokenService;
        this.setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl("/api/auth/login");
        setPostOnly(true);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if ("application/json".equals(request.getHeader("Content-Type"))) {
            try {
                StringBuilder sb = new StringBuilder();
                String line = null;

                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                ObjectMapper mapper = new ObjectMapper();
                UserLogin login = mapper.readValue(sb.toString(), UserLogin.class);

                this.jsonUsername = login.getUsername();
                this.jsonPassword = login.getPassword();

            } catch (Exception e) {
            }
        }

        return super.attemptAuthentication(request, response);
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return jsonPassword;
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return jsonUsername;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User)authResult.getPrincipal();
        String token = tokenService.createToken(user.getUsername());
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        logger.warn("Invalid login attempt for username={}", jsonUsername);

        super.unsuccessfulAuthentication(request, response, failed);
    }
}
