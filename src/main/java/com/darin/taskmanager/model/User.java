package com.darin.taskmanager.model;

import com.darin.taskmanager.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false) // not null is prohibited
    @JsonIgnore // will not show in table data
    private String password;

    @Enumerated(EnumType.STRING) // role will be exact string in enums, not boolean index
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // user has multiple tasks
    @JsonManagedReference(value = "user-tasks")
    private List<Task> tasks = new ArrayList<>();
}
