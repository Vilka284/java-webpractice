package com.springapp.andrii.repository.customize;

import com.springapp.andrii.model.Order;
import com.springapp.andrii.model.User;

import java.util.List;

public class SupplementedOrderRepositoryImpl implements SupplementedOrderRepository {

    @Override
    public List<Order> getUserOrders(User user) {
        return null;
    }

    @Override
    public List<Order> getClosedUserOrders(User user) {
        return null;
    }
}
