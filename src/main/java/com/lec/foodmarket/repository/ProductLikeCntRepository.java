package com.lec.foodmarket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.foodmarket.domain.Member;
import com.lec.foodmarket.domain.Product;
import com.lec.foodmarket.domain.ProductLikeCnt;

public interface ProductLikeCntRepository extends JpaRepository<ProductLikeCnt, Long> {
	// 회원과 상품 검색
	Optional<ProductLikeCnt> findByMemberAndProduct(Member member, Product product);
	
	// 회원과 상품으로 삭제
	void deleteByMemberAndProduct(Member member, Product product);
	
	// 특정 상품 좋아요 개수
	int countByProduct(Product product);
}
