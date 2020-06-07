package com.andrii.module.order;


import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Getter
    @Setter
    private int userId;

    @Getter
    @Setter
    private List<Integer> orderedItemsId;

}
