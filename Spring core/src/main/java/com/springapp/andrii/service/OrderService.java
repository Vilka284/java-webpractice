package com.springapp.andrii.service;

import com.springapp.andrii.exception.ResourceAlreadyExistsException;
import com.springapp.andrii.exception.ResourceNotFoundException;
import com.springapp.andrii.model.Item;
import com.springapp.andrii.model.Order;
import com.springapp.andrii.repository.OrderItemRepository;
import com.springapp.andrii.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IService<Order> {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order get(long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exists"));
    }

    @Override
    public List<Order> getAll() {
        return (List<Order>) orderRepository.findAll();
    }

    @Override
    public void save(Order order) {
        if (!exist(order))
            orderRepository.save(order);
        else
            throw new ResourceAlreadyExistsException("Order already exist!");

    }

    @Override
    public void update(Order order, long id) {
        Order orderToUpdate = get(id);
        orderToUpdate.setUser(order.getUser());
        orderRepository.save(order);
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public boolean exist(Order order) {
        return orderRepository.existsById(order.getId());
    }
}
