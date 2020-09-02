package com.steven.hicks.configuration;

import com.steven.hicks.filters.JwtAuthFilter;
import com.steven.hicks.filters.JwtAuthorizationFilter;
import com.steven.hicks.services.JwtTokenService;
import com.steven.hicks.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenService m_jwtTokenService;
    @Autowired
    private UserDetailsService m_userDetailsService;

    /**
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .requestMatchers()
                .antMatchers("/api/auth/isLoggedIn")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(new JwtTokenAuthFilter(m_jwtTokenService),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(getAuthenticationEntryPoint());
    }
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .addFilter(new JwtAuthFilter(authenticationManager(), m_jwtTokenService))
                .authorizeRequests(authRequests -> {
                    authRequests
                            .antMatchers("/api/auth/isLoggedIn")
                            .authenticated()
                            .anyRequest()
                            .permitAll();
                })
                .addFilterAfter(
                        new JwtAuthorizationFilter(authenticationManager(), m_jwtTokenService),
                        JwtAuthFilter.class
                )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /*
    @Bean
    public AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return (HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) -> {
                System.out.println("Authentication failed");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed pardner");
            };
    }
    */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(m_userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public BCrypt getBcrypt() {
        return new BCrypt();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "https://localhost:3000",
                "http://localhost:3000",
                "http://127.0.0.1:3000"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Access-Control-Allow-Origin"));
        configuration.setExposedHeaders(List.of("Access-Control-Allow-Origin"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

//    @Configuration
//    @Order(2)
//    public static class AdminRequestSecurity extends WebSecurityConfigurerAdapter {
//
//        @Autowired
//        private JwtTokenService m_jwtTokenService;
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .csrf().disable()
//                    .requestMatchers()
//                    .antMatchers("/album/topRated")
//                    .and()
//                    .authorizeRequests()
//                    .anyRequest()
//                    .hasRole("ADMIN")
//                    .and()
//                    .addFilterBefore(new JwtTokenAuthFilter(m_jwtTokenService),
//                            UsernamePasswordAuthenticationFilter.class);        }
//    }

//    @Configuration
//    @Order(3)
//    public static class EasyRequestSecurity extends WebSecurityConfigurerAdapter {
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .csrf().disable()
//                    .authorizeRequests()
//                    .antMatchers("/**")
//                    .permitAll();
//        }
//    }
}
