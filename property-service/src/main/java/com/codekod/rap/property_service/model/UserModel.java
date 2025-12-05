package com.codekod.rap.property_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Table @Entity(name = "user_details")
@NoArgsConstructor @AllArgsConstructor
@Data
public class UserModel implements UserDetails {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email_id", unique = true, nullable = false)
    private String emailId;

    @Column(name = "user_password", nullable = false)
    private String userPassword;


    /**
     * Using a Set for user roles in your entity is considered the industry standard because:
     * 	• Set guarantees uniqueness: A user cannot have the same role twice. In security logic, duplicate roles provide no additional permission and can create confusion during checks.
     * 	• Set supports fast lookup and prevents accidental duplicates when adding/removing roles, which is common in multi-role environments.
     * 	• Most role-based access patterns care only about whether a user has a role, not the order of those roles. Permissions are checked by presence, not by index or order.
     * Using a List is only required if your design depends on the order of roles (very rare in practice, mostly in legacy or workflow-based systems).
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id")
              )
    private Set<UserRole> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getRoleCd()))
                .toList();
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return emailId;
    }

}
