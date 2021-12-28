package com.lec.foodmarket.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lec.foodmarket.domain.Product;
import com.lec.foodmarket.domain.ProductCategory;
import com.lec.foodmarket.repository.CartRepository;
import com.lec.foodmarket.repository.ProductCategoryRepository;
import com.lec.foodmarket.repository.ProductLikeCntRepository;
import com.lec.foodmarket.repository.ProductRepository;

@Service
public class ProductService {
	

	private ProductRepository productRepository; // 상품
	private ProductCategoryRepository productCategoryRepository; // 상품 카테고리
	private ProductLikeCntRepository productLikeCntRepository; // 상품 좋아요
	private CartRepository cartRepository; // 장바구니
	
	@Autowired
	public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository,
			ProductLikeCntRepository productLikeCntRepository, CartRepository cartRepository) {
		this.productRepository = productRepository;
		this.productCategoryRepository = productCategoryRepository;
		this.productLikeCntRepository = productLikeCntRepository;
		this.cartRepository = cartRepository;
	}
	
	public ProductService() {;}
	
	/******************************************
	 * 관리자
	 *   상품(등록, 조회, 수정, 삭제)
	 *   엑셀을 이용한 일괄 등록(이미지 어떻게 할까 생각해야됨)
	 *   상세설명(에디터 사용 Text타입으로 등록)
	 *   
	 * 사용자
	 *   상품 전체조회, 카테고리조회, 검색조회
	 *   장바구니
	 *   
	 * 추가사항이나 수정사항 알아서
	 ******************************************/
	
	
	/******************************************
	 * 관리자
	 ******************************************/
	// 상품 카테고리 조회
	public List<ProductCategory> productCategorySelect() {
		return productCategoryRepository.findAll();
	}
	
	// 상품 등록
	public void productSave(Product product) {
		productRepository.save(product);
	}
	
	// 모든 상품 검색
	public List<Product> productAllSelecet() {
		return productRepository.findAll();
	}

	// 상품명으로 상품 검색
	public List<Product> productNameSelecet(String name) {
		return productRepository.findByNameContainsIgnoreCase(name);
	}
	
	// 추가 상품명으로 상품 검색
	public List<Product> productDescriptionSelect(String description) {
		return productRepository.findByDescriptionContainsIgnoreCase(description);
	}
	
	// 상품번호로 상품 검색
	public Optional<Product> productProductNoSelect(Long productNo) {
		return productRepository.findById(productNo);
	}
	
	// 상품 수정 날짜로 상품 검색
	public List<Product> productUpdateAtSelect(LocalDateTime from, LocalDateTime to) {
		return productRepository.findByUpdatedAtBetween(from, to);
	}
	
	// 상품 수정 날짜와 상품번호 검색
	public List<Product> productUpdateAtAndProductNo(Long productNo, LocalDateTime from, LocalDateTime to) {
		return productRepository.findByProductNoAndUpdatedAtBetween(productNo, from, to);
	}

	// 상품 수정 날짜와 추가 상품명 검색
	public List<Product> productUpdateAtAndDescription(String description, LocalDateTime from, LocalDateTime to) {
		return productRepository.findByDescriptionContainsIgnoreCaseAndUpdatedAtBetween(description, from, to);
	}
	
	// 상품 수정 날짜와 상품명 검색
	public List<Product> productUpdateAtAndName(String name, LocalDateTime from, LocalDateTime to) {
		return productRepository.findByNameContainsIgnoreCaseAndUpdatedAtBetween(name, from, to);
	}
	
	
	
	
	
	/******************************************
	 * 사용자
	 ******************************************/
	// TODO
	
	
	
	
	
	
	
}
