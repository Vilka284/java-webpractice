package com.springapp.andrii.controller;

import com.springapp.andrii.model.Item;
import com.springapp.andrii.model.Order;
import com.springapp.andrii.model.OrderItem;
import com.springapp.andrii.model.User;
import com.springapp.andrii.service.OrderItemService;
import com.springapp.andrii.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/orders/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.get(id);
    }

    @PostMapping("/orders")
    public ResponseEntity<?> addOrder(@Valid @RequestBody Item item,
                                      @Valid @RequestBody User user) {
        Order order = new Order();
        order.setUser(user);
        orderService.save(order);
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setItem(item);
        orderItemService.save(orderItem);
        return (orderService.exist(order) && orderItemService.exist(orderItem))
                ? ResponseEntity.ok().build()
                : ResponseEntity.noContent().build();
    }

    @PutMapping("/orders/{orderId}/{orderItemId}")
    public ResponseEntity<?> updateOrder(@PathVariable Long orderId,
                                         @PathVariable Long orderItemId,
                                         @Valid @RequestBody Item item,
                                         @Valid @RequestBody User user) {
        Order order = new Order();
        order.setUser(user);
        orderService.update(order, orderId);
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setItem(item);
        orderItemService.update(orderItem, orderItemId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        Order order = orderService.get(id);
        orderService.delete(order);
        return (!orderService.exist(order))
                ? ResponseEntity.ok().build()
                : ResponseEntity.noContent().build();
    }
}
