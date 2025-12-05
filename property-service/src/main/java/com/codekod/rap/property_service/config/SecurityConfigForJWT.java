package com.codekod.rap.property_service.config;

import com.codekod.rap.property_service.security.JWTAuthenticationFilter;
import com.codekod.rap.property_service.security.JWTAuthenticationProvider;
import com.codekod.rap.property_service.security.JWTValidationFilter;
import com.codekod.rap.property_service.service.UserDataService;
import com.codekod.rap.property_service.util.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
public class SecurityConfigForJWT {



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public JWTAuthenticationProvider jwtAuthenticationProvider(UserDetailsService userDetailsService, JWTUtil jwtUtil){
        return  new JWTAuthenticationProvider(jwtUtil, userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider daoAuthenticationProvider,
                                                       JWTAuthenticationProvider jwtAuthenticationProvider){
        return new ProviderManager(Arrays.asList(daoAuthenticationProvider, jwtAuthenticationProvider));
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   AuthenticationManager authenticationManager,
                                                   JWTUtil jwtUtil) throws Exception {

        //Authentication filter responsible for Login
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager, jwtUtil);
        JWTValidationFilter jwtValidationFilter = new JWTValidationFilter(authenticationManager);

        httpSecurity.authorizeHttpRequests( auth -> auth
                                                .requestMatchers("/user/**").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/**").hasRole("USR")
                                                .requestMatchers("/api/**").hasRole("OWN")
                                                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtValidationFilter, JWTAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
