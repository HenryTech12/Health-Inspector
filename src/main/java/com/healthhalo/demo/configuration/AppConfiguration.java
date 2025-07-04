package com.healthhalo.demo.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.healthhalo.demo.dto.UserData;
import com.healthhalo.demo.filter.AuthFilter;
import com.healthhalo.demo.filter.JwtFilter;
import com.healthhalo.demo.response.AuthResponse;
import com.healthhalo.demo.service.JwtService;
import com.healthhalo.demo.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
public class AppConfiguration {

    private String [] publicUrls = {
        "/api/user/create", "/v3/api-docs/**",    // OpenAPI JSON
            "/swagger-ui.html",   // Swagger UI HTML entrypoint
            "/swagger-ui/**",     // Swagger UI resources (JS, CSS)
            "/webjars/**",        // (optional, legacy)
            "/actuator/**",
            "/api/auth/login",
    };

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private JwtService jwtService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService getUserDetailsService() {
        return myUserDetailsService;
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    public AuthFilter getAuthFilter(AuthenticationManager authenticationManager) {
        AuthFilter authFilter = new AuthFilter();
        authFilter.setAuthenticationManager(authenticationManager);
        authFilter.setFilterProcessesUrl("/api/auth/login"); //login endpoints

        authFilter.setAuthenticationSuccessHandler((request,response,authentication) -> {
                response.setStatus(HttpServletResponse.SC_OK);
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

                UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
                UserData userData = userService.fetchDataByUsername(userPrincipal.getUsername());
                AuthResponse authResponse = new AuthResponse(userData.getUsername(),userData.getEmail(),userData.getRole(),jwtService.generateToken(userData));
                response.getWriter().write(objectMapper.writeValueAsString(authResponse));
        });

        authFilter.setAuthenticationFailureHandler(((request, response, exception) -> {
              response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
              response.getWriter().write("Login Failure "+exception.getMessage());
              log.error("login error: {}",exception.getMessage());
        }));


        return authFilter;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager manager) throws Exception {
        http.
            csrf(CsrfConfigurer::disable).
                authorizeHttpRequests(requests -> {
            requests.requestMatchers(publicUrls)
                    .permitAll()
                    .anyRequest().authenticated();
        })
                .addFilterAt(getAuthFilter(manager), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtFilter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
