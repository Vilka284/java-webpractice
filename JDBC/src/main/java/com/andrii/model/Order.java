package com.andrii.model;


import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private int userId;

    private List<Integer> orderedItemsId;

}
