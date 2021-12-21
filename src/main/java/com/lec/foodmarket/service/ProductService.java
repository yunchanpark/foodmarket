package com.lec.foodmarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	
	
	
	
	
	/******************************************
	 * 사용자
	 ******************************************/
	// TODO
	
	
	
	
	
	
	
}
