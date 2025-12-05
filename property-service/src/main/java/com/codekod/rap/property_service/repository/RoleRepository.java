package com.codekod.rap.property_service.repository;

import com.codekod.rap.property_service.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<UserRole, Long> {

    Set<UserRole> findByRoleCdIn(List<String> roleCds);
}
