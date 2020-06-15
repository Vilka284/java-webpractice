package com.springapp.andrii.repository;

import com.springapp.andrii.model.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long>  {
}
