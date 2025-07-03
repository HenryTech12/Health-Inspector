package com.healthhalo.demo.filter;

import com.healthhalo.demo.configuration.MyUserDetailsService;
import com.healthhalo.demo.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtService jwtService = new JwtService();
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if(header != null) {
            token = header.substring(7);
            username = jwtService.extractUsername(token);
            if(username != null && SecurityContextHolder.getContext() == null) {
                UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
                if(!jwtService.validateToken(token,userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
