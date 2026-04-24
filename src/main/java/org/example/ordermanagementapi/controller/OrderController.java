package org.example.ordermanagementapi.controller;

import jakarta.validation.Valid;
import org.example.ordermanagementapi.dto.order.CreateOrderRequest;
import org.example.ordermanagementapi.dto.order.OrderResponse;
import org.example.ordermanagementapi.dto.order.PatchOrderRequest;
import org.example.ordermanagementapi.dto.order.UpdateOrderRequest;
import org.example.ordermanagementapi.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/{id}")
    public OrderResponse findOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping
    public List<OrderResponse> findAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/{id}")
    public OrderResponse updateOrder(@PathVariable Long id, @Valid @RequestBody UpdateOrderRequest request) {
        return orderService.updateOrder(id, request);
    }

    @PatchMapping("/{id}")
    public OrderResponse patchOrder(@PathVariable Long id, @RequestBody PatchOrderRequest request) {
        return orderService.patchOrder(id, request);
    }
}

