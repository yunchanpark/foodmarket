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
public class NoticeSearchDTO {
	
	private String keyword;
	
	private String searchKeyword;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate selectStartDate;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate selectEndDate;
	

}
