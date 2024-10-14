package com.taskmaster.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long created_at;
    private long updated_at;
    private String name;
    private String key;
    private String url;
    private String description;
    private boolean is_active;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "project_users", // name of the join table
            joinColumns = @JoinColumn(name = "project_id"), // foreign key in join table to Project
            inverseJoinColumns = @JoinColumn(name = "user_id") // foreign key in join table to User
    )
    private Set<User> _users = new HashSet<>();

    public Set<User> get_users() {
        return _users;
    }

    public void set_users(Set<User> users) {
        this._users = users;
    }

    public void addUser(User user) {
        this._users.add(user);
        user.get_projects().add(this);
    }

    public void removeUser(User user) {
        this._users.remove(user);
        user.get_projects().remove(this);
    }
}
