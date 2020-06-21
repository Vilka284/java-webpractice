package com.springapp.andrii.service;

import com.springapp.andrii.exception.ResourceNotFoundException;
import com.springapp.andrii.model.Order;
import com.springapp.andrii.model.User;
import com.springapp.andrii.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order get(long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exists"));
    }

    public List<Order> getAll() {
        return (List<Order>) orderRepository.findAll();
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public void update(Order order, long id) {
        Order orderToUpdate = get(id);
        orderToUpdate.setUser(order.getUser());
        save(order);
    }

    public void delete(Order order) {
        orderRepository.delete(order);
    }

    public boolean exist(Order order) {
        return orderRepository.existsById(order.getId());
    }

    public List<Order> getClosedOrders(User user) {
        return orderRepository.getClosedUserOrders(user);
    }

    public void closeOrder(Order order) {
        order.setStatus("closed");
        update(order, order.getId());
    }
}
