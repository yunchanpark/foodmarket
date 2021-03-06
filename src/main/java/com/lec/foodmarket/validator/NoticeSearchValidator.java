package com.lec.foodmarket.validator;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.lec.foodmarket.domain.dto.NoticeSearchDTO;

@Component
public class NoticeSearchValidator implements Validator{
	
	@Override
	public boolean supports(Class<?> clazz) {
		return NoticeSearchDTO.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		NoticeSearchDTO dto = (NoticeSearchDTO) target;
		LocalDate noticeselectStartDate = dto.getSelectStartDate();
		LocalDate noticeselectEndDate = dto.getSelectEndDate();

	
		
		if(noticeselectStartDate != null && noticeselectEndDate == null) {
			errors.rejectValue("selectEndDate", "검색 종료 날짜도 입력해주세요.");
		}
		
		if(noticeselectStartDate == null && noticeselectEndDate != null) {
			errors.rejectValue("selectStartDate", "검색 시작 날짜도 입력해주세요.");
		}
		
		if (noticeselectStartDate != null && noticeselectEndDate != null) {
			if ((noticeselectEndDate.compareTo(noticeselectStartDate) < 0)) errors.rejectValue("selectStartDate", "검색 시작 날짜가 검색 종료 날짜보다 클 수 없습니다.");
		}
	}
	
	
	

}
