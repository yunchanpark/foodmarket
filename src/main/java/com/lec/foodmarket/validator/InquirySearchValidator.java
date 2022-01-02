package com.lec.foodmarket.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.lec.foodmarket.domain.dto.InquirySearchDTO;

@Component
public class InquirySearchValidator implements Validator{
	
	@Override
	public boolean supports(Class<?> clazz) {
		return InquirySearchDTO.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		InquirySearchDTO dto = (InquirySearchDTO) target;
		LocalDate inquiryselectStartDate = dto.getSelectStartDate();
		LocalDate inquiryselectEndDate = dto.getSelectEndDate();

	
		
		if(inquiryselectStartDate != null && inquiryselectEndDate == null) {
			errors.rejectValue("selectEndDate", "검색 종료 날짜도 입력해주세요.");
		}
		
		if(inquiryselectStartDate == null && inquiryselectEndDate != null) {
			errors.rejectValue("selectStartDate", "검색 시작 날짜도 입력해주세요.");
		}
		
		if (inquiryselectStartDate != null && inquiryselectEndDate != null) {
			if ((inquiryselectEndDate.compareTo(inquiryselectStartDate) < 0)) errors.rejectValue("selectStartDate", "검색 시작 날짜가 검색 종료 날짜보다 클 수 없습니다.");
		}
	}
	
	
	

}
