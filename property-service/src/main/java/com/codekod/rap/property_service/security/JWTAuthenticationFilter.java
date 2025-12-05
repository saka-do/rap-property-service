package com.codekod.rap.property_service.security;


import com.codekod.rap.property_service.dto.AuthDto;
import com.codekod.rap.property_service.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if(!request.getServletPath().equals("/user/login")){
            filterChain.doFilter(request,response);
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        AuthDto authData = objectMapper.readValue(request.getInputStream(), AuthDto.class);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(authData.emailId(), authData.passwd());

        Authentication authResult;
        try {
            authResult = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException ex) {
            sendErrorMessage(response, HttpStatus.UNAUTHORIZED, "Invalid Credentials");
            return;   // STOP FILTER CHAIN
        } catch (UsernameNotFoundException ex) {
            sendErrorMessage(response, HttpStatus.BAD_REQUEST, "User not found");
            return;
        }

        boolean hasRole = authResult.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authData.selectedRole()));
        if(!hasRole) {
            sendErrorMessage(response, HttpStatus.FORBIDDEN, "User does not have selected role");
            return;
        }

        String token = jwtUtil.generateToken(authResult.getName(), 10);
        response.setHeader("Authorization", "Bearer "+ token);

    }


    private void sendErrorMessage(HttpServletResponse response,
                                  HttpStatus status,
                                  String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> body = new HashMap<>();

        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        new ObjectMapper().writeValue(response.getOutputStream(),body);

    }
}
