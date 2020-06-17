package com.springapp.andrii.service;

import com.springapp.andrii.exception.ResourceNotFoundException;
import com.springapp.andrii.model.OrderItem;
import com.springapp.andrii.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderItemService implements IService<OrderItem> {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem get(long id) {
        return orderItemRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not exists"));
    }

    @Override
    public List<OrderItem> getAll() {
        return (List<OrderItem>) orderItemRepository.findAll();
    }

    @Override
    public void save(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    @Override
    public void update(OrderItem orderItem, long id) {
        OrderItem orderItemToUpdate = get(id);
        orderItemToUpdate.setItem(orderItem.getItem());
        orderItemToUpdate.setOrder(orderItem.getOrder());
        save(orderItemToUpdate);
    }

    @Override
    public void delete(OrderItem orderItem) {
        orderItemRepository.delete(orderItem);
    }

    @Override
    public boolean exist(OrderItem orderItem) {
        return orderItemRepository.existsById(orderItem.getId());
    }
}
