package com.lec.foodmarket.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperatorDTO {
	
	private String operatorName;
	private String operatorId;
	private String operatorPw;
}
