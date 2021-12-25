package com.lec.foodmarket.domain.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.lec.foodmarket.domain.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
	// 카테고리 번호
	private ProductCategory categoryNo;
	
	// 상품 이름
	private String name;
	
	// 상품 간단 설명
	private String description;
	
	// 상품가격
	private Integer price;
	
	// 상품 매입가
	private Integer purchasePrice;
	
	// 상품 재고
	private Integer stock;
	
	// 상품 할인
	private Integer discount;
	
	// 상품 할인 구분(ex. percent, won)
	private String exchangeRate;
	
	// 상품 상세 설명
	private String detailContent;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime startDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime endDate;

	// 상품 할인 체크
	private String discountCk;
	
	// 상품 기간 할인 설정
	private String duringCheck;
}
