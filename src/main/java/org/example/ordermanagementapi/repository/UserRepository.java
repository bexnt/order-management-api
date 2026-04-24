package org.example.ordermanagementapi.repository;

import org.example.ordermanagementapi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT DISTINCT u FROM UserEntity u LEFT JOIN FETCH u.orders")
    List<UserEntity> findAllWithOrders();
}

