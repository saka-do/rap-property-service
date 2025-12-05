package com.codekod.rap.property_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table @Entity(name = "roles")
@Data @AllArgsConstructor @NoArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "role_cd", unique = true, nullable = false)
    private String roleCd;

    @Column(name = "role_name", unique = true, nullable = false)
    private String role;
}
