package com.lec.foodmarket.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.foodmarket.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	// Like %?% 검색 대소문자 구분 없이
	List<Product> findByNameContainsIgnoreCase(String name);
	List<Product> findByDescriptionContainsIgnoreCase(String description);
	
	// Between 날짜 사이
	List<Product> findByUpdatedAtBetween(LocalDateTime from, LocalDateTime to);
	
	// And, Between
	List<Product> findByNameContainsIgnoreCaseAndUpdatedAtBetween(String name, LocalDateTime from, LocalDateTime to);
	List<Product> findByDescriptionContainsIgnoreCaseAndUpdatedAtBetween(String description,LocalDateTime from, LocalDateTime to);
	List<Product> findByProductNoAndUpdatedAtBetween(Long productNo, LocalDateTime from, LocalDateTime to);
}
