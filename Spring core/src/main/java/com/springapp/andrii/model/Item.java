package com.springapp.andrii.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "item", schema = "public", catalog = "java_task")
public class Item {
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "item_name")
    private String name;

    @Basic
    @Column(name = "price")
    private float price;

    @Basic
    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    private Group group;
}