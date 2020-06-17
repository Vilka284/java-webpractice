package com.springapp.andrii.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role", schema = "public", catalog = "java_task")
public class Role {
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "role_name")
    @NonNull
    private String name;
}
