package com.taskmaster.user.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.taskmaster.project.entity.ProjectUserRole;
import com.taskmaster.role.entity.Role;
import com.taskmaster.task.entity.Task;
import com.taskmaster.exception.UserLevelException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "designation")
    private String designation;

    @Column(name = "company")
    private String company;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)  // Application-level role
    private Role applicationRole;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Prevents recursion with ProjectUserRole
    private Set<ProjectUserRole> projectUserRoles = new HashSet<>();

    @OneToMany(mappedBy = "createdBy")
    private List<Task> createdBy = new ArrayList<>();

    @OneToMany(mappedBy = "assignedTo")
    private List<Task> assignedTo = new ArrayList<>();

    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(applicationRole.getName().toString());
        return List.of(authority);
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public User orElseReturn() {
        return null;
    }

    public UserDetails orElseThrow() throws UserLevelException {
        throw new UserLevelException("User not found");
    }
}
