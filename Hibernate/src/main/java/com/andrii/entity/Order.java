package com.andrii.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order", schema = "public", catalog = "java_task")
public class Order {
    @Id
    @Column(name = "id")
    private int id;
    @ManyToOne
    @Column(name = "user_id")
    private User user;

}
