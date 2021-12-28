package com.lec.foodmarket.domain.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchDTO {
	// 상품 검색 카테고리
	private String keyword;
	
	// 상품 검색 키워드
	private String searchKeyword;
	
	// 상품 검색 시작 날짜
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate selectStartDate;
	
	// 상품 검색 끝 날짜
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate selectEndDate;
}
