package com.springapp.andrii.repository;

import com.springapp.andrii.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
