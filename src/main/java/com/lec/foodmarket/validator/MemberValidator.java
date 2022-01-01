package com.lec.foodmarket.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.lec.foodmarket.domain.dto.MemberSerchDTO;

@Component
public class MemberValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return MemberSerchDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MemberSerchDTO dto = (MemberSerchDTO) target;
		LocalDate selectStartDate = dto.getSelectStartDate();
		LocalDate selectEndDate = dto.getSelectEndDate();

		if (selectStartDate != null && selectEndDate == null) {
			errors.rejectValue("selectEndDate", "검색 끝나는 날짜도 입력해주세요");
		}

		if (selectStartDate == null && selectEndDate != null) {
			errors.rejectValue("selectStartDate", "검색 시작하는 날짜도 입력해주세요");
		}

		if (selectStartDate != null && selectEndDate != null) {
			if ((selectEndDate.compareTo(selectStartDate) < 0))
				errors.rejectValue("selectStartDate", "검색 시작 시간이 검색 끝시간 보다 클 수 없습니다.");
		}
	}
}
