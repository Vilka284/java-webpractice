package com.springapp.andrii.service;

import com.springapp.andrii.exception.ResourceNotFoundException;
import com.springapp.andrii.model.OrderItem;
import com.springapp.andrii.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderItem get(long id) {
        return orderItemRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not exists"));
    }

    public List<OrderItem> getAll() {
        return (List<OrderItem>) orderItemRepository.findAll();
    }

    public void save(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    public void update(OrderItem orderItem, long id) {
        OrderItem orderItemToUpdate = get(id);
        orderItemToUpdate.setItem(orderItem.getItem());
        orderItemToUpdate.setOrder(orderItem.getOrder());
        save(orderItemToUpdate);
    }

    public void delete(OrderItem orderItem) {
        orderItemRepository.delete(orderItem);
    }

    public boolean exist(OrderItem orderItem) {
        return orderItemRepository.existsById(orderItem.getId());
    }
}
