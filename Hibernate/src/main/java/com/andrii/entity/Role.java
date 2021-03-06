package com.andrii.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "role", schema = "public", catalog = "java_task")
public class Role {
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "role_name")
    private String name;
}
