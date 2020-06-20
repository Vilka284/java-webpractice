package com.springapp.andrii.repository;

import com.springapp.andrii.model.Order;
import com.springapp.andrii.repository.modify.ModifiedOrderRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>, ModifiedOrderRepository {
}
