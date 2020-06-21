package com.springapp.andrii.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order", schema = "public", catalog = "java_task")
public class Order {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "status")
    @NotNull
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}