package com.springapp.andrii.controller;

import com.springapp.andrii.model.Item;
import com.springapp.andrii.model.Order;
import com.springapp.andrii.model.OrderItem;
import com.springapp.andrii.service.OrderItemService;
import com.springapp.andrii.service.OrderService;
import com.springapp.andrii.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private UserService userService;

    @GetMapping("/orders")
    public List<Order> getOrder() {
        return orderService.getAll();
    }

    @GetMapping("/orders/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.get(id);
    }

    @GetMapping("/orders/closed/{userId}")
    public List<Order> getClosedOrders(@PathVariable Long userId) {
        return orderService.getClosedOrders(userService.get(userId));
    }

    @PostMapping("/orders/{userId}")
    public ResponseEntity<?> createOrder(@PathVariable Long userId,
                                         @Valid @RequestBody Item item) {
        Order order = new Order();
        order.setUser(userService.get(userId));
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
                                         @Valid @RequestBody Item item) {
        Order order = new Order();
        order.setUser(orderService.get(orderId).getUser());
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
