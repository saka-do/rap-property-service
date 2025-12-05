package com.codekod.rap.property_service.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({ IllegalArgumentException.class, UsernameNotFoundException.class })
    public ResponseEntity<ErrorResponse> handleBadRequestExceptions(RuntimeException e) {
        ErrorResponse error = new ErrorResponse("BAD_REQUEST", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialException(Exception e){
        System.out.println("In Handler");
        ErrorResponse error = new ErrorResponse("BAD_CREDENTIAL", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }


    @Getter @Setter
    public static class ErrorResponse{
        private String code;
        private String message;

        ErrorResponse(String code, String message){
            this.code = code;
            this.message = message;
        }
    }
}
