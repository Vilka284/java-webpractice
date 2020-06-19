package com.springapp.andrii.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Entity
@Table(name = "item", schema = "public", catalog = "java_task")
public class Item {
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "item_name")
    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @Basic
    @Column(name = "price")
    @NotNull
    @Min(0)
    private float price;

    @Basic
    @Column(name = "quantity")
    @NotNull
    @Min(0)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    private Group group;
}