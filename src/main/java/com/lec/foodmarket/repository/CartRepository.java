package com.lec.foodmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.foodmarket.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
