package org.example.ordermanagementapi.controller;

import org.example.ordermanagementapi.dto.order.CreateOrderRequest;
import org.example.ordermanagementapi.dto.user.CreateUserRequest;
import org.example.ordermanagementapi.dto.user.UserResponse;
import org.example.ordermanagementapi.dto.user.UserWithOrderResponse;
import org.example.ordermanagementapi.service.OrderService;
import org.example.ordermanagementapi.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/{id}/with-orders")
    public UserWithOrderResponse findUserWithOrdersById(@PathVariable Long id) {
        return userService.getUserWithOrdersById(id);
    }

    @GetMapping("/with-orders")
    public List<UserWithOrderResponse> findAllUsersWithOrders() {
        return userService.getAllUsersWithOrders();
    }

    @GetMapping("/{id}")
    public UserResponse findUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserResponse> findAllUsers() {
        return userService.getAllUsers();
    }

}

