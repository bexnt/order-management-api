package org.example.ordermanagementapi.service;

import org.example.ordermanagementapi.dto.user.CreateUserRequest;
import org.example.ordermanagementapi.dto.user.UserOrderResponse;
import org.example.ordermanagementapi.dto.user.UserResponse;
import org.example.ordermanagementapi.dto.user.UserWithOrderResponse;
import org.example.ordermanagementapi.entity.OrderEntity;
import org.example.ordermanagementapi.entity.UserEntity;
import org.example.ordermanagementapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(CreateUserRequest request) {
        UserEntity savedUser = userRepository.save(mapToUserEntity(request));
        return mapToResponse(savedUser);
    }

    public UserResponse getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        return mapToResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse).toList();
    }

    public List<UserWithOrderResponse> getAllUsersWithOrders() {
        return userRepository.findAllWithOrders()
                .stream()
                .map(this::mapToUserWithOrderResponse)
                .toList();
    }

    public UserWithOrderResponse getUserWithOrdersById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        return mapToUserWithOrderResponse(user);
    }

    private UserResponse mapToResponse(UserEntity user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setName(user.getName());

        return response;
    }

    private UserEntity mapToUserEntity(CreateUserRequest request) {
        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        return user;
    }

    private UserWithOrderResponse mapToUserWithOrderResponse(UserEntity user) {
        UserWithOrderResponse response = new UserWithOrderResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        List<UserOrderResponse> orders = user.getOrders()
                .stream()
                .map(this::mapToUserOrderResponse)
                .toList();
        response.setOrders(orders);
        return response;
    }

    private UserOrderResponse mapToUserOrderResponse(OrderEntity order) {
        UserOrderResponse response = new UserOrderResponse();
        response.setId(order.getId());
        response.setDescription(order.getDescription());
        response.setAmount(order.getAmount());
        response.setCreatedAt(order.getCreatedAt());
        return response;
    }
}

