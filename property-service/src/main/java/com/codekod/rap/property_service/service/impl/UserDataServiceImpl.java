package com.codekod.rap.property_service.service.impl;

import com.codekod.rap.property_service.dto.AuthDto;
import com.codekod.rap.property_service.dto.UserDto;
import com.codekod.rap.property_service.model.UserModel;
import com.codekod.rap.property_service.model.UserRole;
import com.codekod.rap.property_service.repository.RoleRepository;
import com.codekod.rap.property_service.repository.UserDetailsRepository;
import com.codekod.rap.property_service.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserDataServiceImpl implements UserDataService {

    private final UserDetailsRepository userDetailsRepo;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDataServiceImpl(UserDetailsRepository userDetailsRepo,
                               PasswordEncoder passwordEncoder,
                               RoleRepository roleRepository){
        this.userDetailsRepo = userDetailsRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public String registerUser(UserDto userData) {

        if(userDetailsRepo.existsByEmailId(userData.getEmailId())) {
            throw new IllegalArgumentException("User already registered with emailId: "+ userData.getEmailId());
        }

        Set<UserRole> userRoles = roleRepository.findByRoleCdIn(userData.getRoles());
        if(userRoles.isEmpty()) throw new IllegalArgumentException("Provided Role Codes not exist");

        UserModel user = new UserModel();
        user.setFullName(userData.getFullName());
        user.setUserPassword(passwordEncoder.encode(userData.getPassword()));
        user.setEmailId(userData.getEmailId());
        user.setRoles(userRoles);
        user = userDetailsRepo.save(user);
        return user.getUsername() + "Registered Successfully";
    }

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {

        return userDetailsRepo.findByEmailId(emailId).orElseThrow(() -> new UsernameNotFoundException("User not found : "+ emailId));
    }
}
