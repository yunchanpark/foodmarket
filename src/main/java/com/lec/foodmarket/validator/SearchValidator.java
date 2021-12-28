package com.lec.foodmarket.validator;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.lec.foodmarket.domain.dto.SearchDTO;

@Component
public class SearchValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return SearchDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SearchDTO dto = (SearchDTO) target;
		String keyword = dto.getKeyword();
		String searchKeyword = dto.getSearchKeyword();
		LocalDate selectStartDate = dto.getSelectStartDate();
		LocalDate selectEndDate = dto.getSelectEndDate();

		// 숫자만 허용 정규표현식
		String regex = "\\d";
		Pattern pat = Pattern.compile(regex);
		Matcher matcher;
		
		if(keyword != null && keyword.equals("productNo")) {
			matcher = pat.matcher(searchKeyword);
			if(!matcher.find()) {
				errors.rejectValue("searchKeyword", "상품번호로 검색 할 때는 숫자로만 입력해야됩니다.");
			}
		}
		
		if(selectStartDate != null && selectEndDate == null) {
			errors.rejectValue("selectEndDate", "검색 끝나는 날짜도 입력해주세요");
		}
		
		if(selectStartDate == null && selectEndDate != null) {
			errors.rejectValue("selectStartDate", "검색 시작하는 날짜도 입력해주세요");
		}
		
		if (selectStartDate != null && selectEndDate != null) {
			if ((selectEndDate.compareTo(selectStartDate) < 0)) errors.rejectValue("selectStartDate", "할인 시작 시간이 할인 끝시간 보다 클 수 없습니다.");
		}
	}
}
