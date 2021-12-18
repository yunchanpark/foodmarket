package com.lec.foodmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.foodmarket.domain.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
