package com.lec.foodmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.foodmarket.domain.ProductLikeCnt;

public interface ProductLikeCntRepository extends JpaRepository<ProductLikeCnt, Long> {

}
