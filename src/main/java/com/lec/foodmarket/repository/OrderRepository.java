package com.lec.foodmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.foodmarket.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
