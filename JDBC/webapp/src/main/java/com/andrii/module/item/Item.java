package com.andrii.module.item;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Getter
    @Setter
    private String itemName;

    @Getter
    @Setter
    private int groupId;

    @Getter
    @Setter
    private float price;

    @Getter
    @Setter
    private int quantity;
}
