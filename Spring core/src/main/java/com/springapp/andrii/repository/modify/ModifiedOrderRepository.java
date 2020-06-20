package com.springapp.andrii.repository.modify;

import com.springapp.andrii.model.Order;
import com.springapp.andrii.model.User;

import java.util.List;

public interface ModifiedOrderRepository {
    List<Order> getUserOrders(User user);
    List<Order> getClosedUserOrders(User user);
}
