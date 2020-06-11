package com.andrii.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String name;
    private int groupId;
    private float price;
    private int quantity;
}
