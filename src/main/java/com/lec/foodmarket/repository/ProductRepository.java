package com.lec.foodmarket.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lec.foodmarket.domain.Product;
import com.lec.foodmarket.domain.ProductCategory;

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

	// 최신 등록일 Top11
	@Query(value = "select * from product order by created_at desc limit 11", nativeQuery = true)
	List<Product> findTop11OrderByCreatedAt();

	// 조회수 Top3
	@Query(value = "select * from product order by product_viewcnt desc limit 3", nativeQuery = true)
	List<Product> findTop3OrderByViewCnt();
	
	// 카테고리 검색
	List<Product> findByCategoryNo(ProductCategory category);
}
