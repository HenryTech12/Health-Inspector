package com.healthhalo.demo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthhalo.demo.request.AuthRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class AuthFilter extends UsernamePasswordAuthenticationFilter {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthRequest authRequest = objectMapper.readValue(request.getInputStream(), AuthRequest.class);
            if(!Objects.isNull(authRequest)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword());
                setDetails(request,authToken);
                return this.getAuthenticationManager().authenticate(authToken);
            }
        }
        catch(IOException ie) {
            System.out.println(ie.getMessage());
            log.error("auth error!!!: {}",ie.getMessage());
        }
        return null;
    }
}
