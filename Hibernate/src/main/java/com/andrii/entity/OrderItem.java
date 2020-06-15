package com.andrii.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_item", schema = "public", catalog = "java_task")
public class OrderItem {
    @Id
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;
    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    private Item item;

}
