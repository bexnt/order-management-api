package org.example.ordermanagementapi.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.ordermanagementapi.dto.order.CreateOrderRequest;
import org.example.ordermanagementapi.dto.order.OrderResponse;
import org.example.ordermanagementapi.dto.order.PatchOrderRequest;
import org.example.ordermanagementapi.dto.order.UpdateOrderRequest;
import org.example.ordermanagementapi.entity.OrderEntity;
import org.example.ordermanagementapi.entity.UserEntity;
import org.example.ordermanagementapi.repository.OrderRepository;
import org.example.ordermanagementapi.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public OrderResponse createOrder(CreateOrderRequest request) {
        UserEntity existingUser = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("No user found with id " + request.getUserId()));
        OrderEntity order = mapToOrderEntity(request, existingUser);
        order.setCreatedAt(LocalDateTime.now());
        OrderEntity savedOrder = orderRepository.save(order);
        return mapToOrderResponse(savedOrder);

    }

    public OrderResponse getOrderById(Long id) {
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No order found with id " + id));
        return mapToOrderResponse(entity);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    @Transactional
    public OrderResponse updateOrder(Long id, UpdateOrderRequest request) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No order found with id " + id));
        order.setDescription(request.getDescription());
        order.setAmount(request.getAmount());
        return mapToOrderResponse(order);
    }

    @Transactional
    public OrderResponse patchOrder(Long id, PatchOrderRequest request) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No order found with id " + id));
        if (request.getAmount() != null) {
            order.setAmount(request.getAmount());
        }
        if (request.getDescription() != null) {
            order.setDescription(request.getDescription());
        }

        return mapToOrderResponse(order);
    }



    private OrderEntity mapToOrderEntity(CreateOrderRequest request, UserEntity user) {
        OrderEntity order = new OrderEntity();
        order.setDescription(request.getDescription());
        order.setAmount(request.getAmount());
        user.addOrder(order);

        return order;
    }

    private OrderResponse mapToOrderResponse(OrderEntity entity) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(entity.getId());
        orderResponse.setAmount(entity.getAmount());
        orderResponse.setCreatedAt(entity.getCreatedAt());
        orderResponse.setDescription(entity.getDescription());
        orderResponse.setUserId(entity.getUser().getId());

        return orderResponse;
    }


}

