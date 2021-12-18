package com.lec.foodmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.foodmarket.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
