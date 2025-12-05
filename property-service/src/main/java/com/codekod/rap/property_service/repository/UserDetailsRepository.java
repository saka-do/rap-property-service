package com.codekod.rap.property_service.repository;

import com.codekod.rap.property_service.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByEmailId(String emailId);

    boolean existsByEmailId(String emailId);
}

