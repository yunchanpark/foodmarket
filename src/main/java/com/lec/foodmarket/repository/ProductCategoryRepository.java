package com.lec.foodmarket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.foodmarket.domain.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
	Optional<ProductCategory> findByName(String name);
}
