package com.springapp.andrii.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user", schema = "public", catalog = "java_task")
public class User {
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "user_name")
    private String name;

    @Basic
    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;
}