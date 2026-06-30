package com.darin.taskmanager.model;

import com.darin.taskmanager.enums.Category;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tasks")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    private boolean isCompleted;

    @ManyToOne
    @JsonBackReference(value = "user-tasks")
    @JoinColumn(name = "user_id")
    private User user;
}
