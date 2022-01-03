package com.lec.foodmarket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lec.foodmarket.domain.Cart;
import com.lec.foodmarket.domain.Member;
import com.lec.foodmarket.domain.Product;

public interface CartRepository extends JpaRepository<Cart, Long> {
	// 이미 장바구니에 담은 상품인지
	Optional<Cart> findByIdAndProductNo(Member member, Product product);
	
	List<Cart> findAllById(Member member);
	
	@Query(value = "select * from cart  where id = ?1 and product_no in ?2", nativeQuery = true)
	List<Cart> findMemberAndProductNoIn(Member member, List<Product> product);
}
