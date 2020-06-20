package com.springapp.andrii.repository;

import com.springapp.andrii.model.Order;
import com.springapp.andrii.repository.customize.SupplementedOrderRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>, SupplementedOrderRepository {
}
