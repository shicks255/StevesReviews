package com.steven.hicks.controllers;

import com.steven.hicks.services.JwtTokenService;
import com.steven.hicks.models.dtos.UserLogin;
import com.steven.hicks.repositories.UserRepository;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private JwtTokenService m_jwtTokenService;
    @Autowired
    private UserRepository m_userRepository;
    @Autowired
    private AuthenticationManager m_authenticationManager;
    @Autowired
    MeterRegistry m_meterRegistry;

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody UserLogin user) {
        try {
            UsernamePasswordAuthenticationToken t =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            m_authenticationManager.authenticate(t);
            String token = m_jwtTokenService.createToken(user.getUsername());

            return ResponseEntity.ok(token);
        }
        catch (Exception e ){
//            throw new BadCredentialsException("Invaled username/password");

            m_meterRegistry.counter("invalidLogin").increment();

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Invalid Username/Passowrd");
        }
    }

    @GetMapping("/isLoggedIn")
    public HttpStatus isLoggedIn(HttpServletRequest request) {
        String token = m_jwtTokenService.resolveToken(request);

        return HttpStatus.OK;
    }
}
