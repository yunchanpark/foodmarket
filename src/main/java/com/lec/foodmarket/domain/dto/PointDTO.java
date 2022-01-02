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
public class PointDTO {

	// 키워드
	private String keyword;
	
	// 키워드 검색 내용
	private String searchKeyword;
	
	// 구분
	private int division;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate selectStartDate;

	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate selectEndDate;

}
