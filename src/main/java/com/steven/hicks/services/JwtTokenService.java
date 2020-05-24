package com.steven.hicks.services;

import com.steven.hicks.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenService {

    @Autowired
    UserDetailsService m_userDetailsService;
    @Value("${jwt.token.key}")
    private String tokenKey;
    @Value("${jwt.token.duration}")
    private Long tokenDuration;
    private String secret;

    @PostConstruct
    public void init() {
        secret = Base64.getEncoder().encodeToString(tokenKey.getBytes());
    }

    public String createToken(String userName) {

        Claims claims = Jwts.claims().setSubject(userName);
        Date now = new Date();
        Date expiration = new Date(now.getTime()+600_000);
//        Date expiration = new Date(now.getTime() + Long.valueOf(tokenDuration));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = m_userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public User getUserFromToken(HttpServletRequest request) {
        String token = resolveToken(request);
        return (User)m_userDetailsService.loadUserByUsername(getUsername(token));
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7);

        return null;
    }

    public boolean validateToken(String token) throws UsernameNotFoundException {
        try {
            Jws<Claims> claims =
                    Jwts.parser()
                            .setSigningKey(secret)
                            .parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            return true;
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Username not found");
        }
    }
}
