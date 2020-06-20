package com.springapp.andrii.repository.customize;

import com.springapp.andrii.model.Order;
import com.springapp.andrii.model.User;

import java.util.List;

public interface SupplementedOrderRepository {
    List<Order> getUserOrders(User user);
    List<Order> getClosedUserOrders(User user);
}
