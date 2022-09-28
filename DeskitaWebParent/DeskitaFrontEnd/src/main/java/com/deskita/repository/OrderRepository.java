package com.deskita.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deskita.common.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
