package com.springapp.andrii.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user", schema = "public", catalog = "java_task")
public class User {
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "user_name")
    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @Basic
    @Column(name = "password")
    @NotNull
    @Size(min = 8, max = 50)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;
}