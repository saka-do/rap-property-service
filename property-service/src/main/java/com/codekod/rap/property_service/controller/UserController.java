package com.codekod.rap.property_service.controller;

import com.codekod.rap.property_service.dto.UserDto;
import com.codekod.rap.property_service.service.UserDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserDataService userDataService;

    UserController(UserDataService userDataService){
        this.userDataService = userDataService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userData){
        return ResponseEntity.ok(
                this.userDataService.registerUser(userData)
        );
    }
}
