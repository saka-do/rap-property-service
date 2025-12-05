package com.codekod.rap.property_service.service;

import com.codekod.rap.property_service.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserDataService extends UserDetailsService {

    String registerUser(UserDto userData);
}
