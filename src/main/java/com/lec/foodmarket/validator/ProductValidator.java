package com.lec.foodmarket.validator;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.lec.foodmarket.domain.ProductCategory;
import com.lec.foodmarket.domain.dto.ProductDTO;

public class ProductValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ProductDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProductDTO dto = (ProductDTO) target;

		ProductCategory categoryNo = dto.getCategoryNo(); // 카테고리 번호
		String name = dto.getName(); // 상품 이름
		Integer price = dto.getPrice(); // 상품가격
		Integer purchasePrice = dto.getPurchasePrice(); // 상품 매입가
		Integer stock = dto.getStock();// 상품 재고
		String discountCk = dto.getDiscountCk(); // 상품 할인 체크
		Integer discount = dto.getDiscount(); // 상품 할인
		String exchangeRate = dto.getExchangeRate(); // 상품 할인 구분(ex. %, 원)
		String duringCheck = dto.getDuringCheck(); // 상품 기간 할인 설정
		LocalDateTime startDate = dto.getStartDate(); // 상품 할인 시작 날짜
		LocalDateTime endDate = dto.getEndDate(); // 상품 할인 끝 날짜

		// 숫자만 허용 정규표현식
		String regex = "\\d";
		Pattern pat = Pattern.compile(regex);
		Matcher matcher;

		// 카테고리 번호 검증
		if (categoryNo == null) {
			errors.rejectValue("categoryNo", "카테고리는 필수사항입니다.");
		}

		// 상품 이름 검증
		if (name == null || name.trim().length() == 0) {
			errors.rejectValue("name", "상품명은 필수사항입니다.");
		}

		// 상품 가격 검증
		if (price == null) {
			errors.rejectValue("price", "판매가는 필수사항입니다.");
		} else {
			String strPrice = Integer.toString(price);
			matcher = pat.matcher(strPrice);

			if (!matcher.find()) {
				errors.rejectValue("price", "판매가는 숫자로만 입력해야됩니다.");
			}
			if (purchasePrice != null) {
				if (price <= purchasePrice) {
					errors.rejectValue("price", "판매가가 매입가보다 작을 수 없습니다.");
				}
			} else if (price <= 0) {
				errors.rejectValue("price", "판매가가 0하고 같거나 작을 수 없습니다.");
			}
		}

		// 상품 매입가 검증
		if (purchasePrice == null) {
			errors.rejectValue("purchasePrice", "매입가는 필수사항입니다.");
		} else {
			String strPurchasePrice = Integer.toString(purchasePrice);
			matcher = pat.matcher(strPurchasePrice);
			if (!matcher.find()) {
				errors.rejectValue("purchasePrice", "매입가는 숫자로만 입력해야됩니다.");
			} else if (purchasePrice <= 0) {
				errors.rejectValue("purchasePrice", "매입가는 0하고 같거나 작을 수 없습니다.");
			}
		}

		// 상품 재고 검증
		if (stock == null) {
			errors.rejectValue("stock", "재고는 필수사항입니다.");
		} else {
			String strStock = Integer.toString(stock);
			matcher = pat.matcher(strStock);

			if (!matcher.find()) {
				errors.rejectValue("stock", "재고는 숫자로만 입력해야됩니다.");
			} else if (stock <= 0) {
				errors.rejectValue("stock", "재고는 0하고 같거나 작을 수 없습니다.");
			}
		}

		// 상품 할인 검증
		if (discountCk != null && !(discountCk.isEmpty()) && discountCk.equals("Y")) {
			if (discount == null) {
				errors.rejectValue("discount", "할인 사용함을 선택하면 즉시할인은 필수입니다.");
			} else {
				if (discount <= 0) {
					errors.rejectValue("discount", "할인가는 0하고 같거나 작을 수 없습니다.");
				} else if (exchangeRate != null && !(exchangeRate.isEmpty())) {
					if (exchangeRate.equals("percent")) {
						if (discount > 100)
							errors.rejectValue("discount", "%는 100이상이 될 수 없습니다.");
					} else if (exchangeRate.equals("won")) {
						if (discount >= price)
							errors.rejectValue("discount", "판매가보다 할인가가 같거나 클 수 없습니다.");
					}
				}
			}
		}

		// 상품 할인 시간 검증
		if (duringCheck != null && duringCheck.equals("Y")) {
			if (startDate == null || endDate == null) {
				errors.rejectValue("startDate", "기간할인설정을 선택하면 시간입력은 필수사항입니다.");
			} else if (startDate != null && endDate != null) {
				LocalDateTime now = LocalDateTime.now();
				if (!(now.isBefore(startDate))) {
					errors.rejectValue("startDate", "현재 시간보다 할인시간이 작을 수 없습니다.");
				} else if (!(startDate.isBefore(endDate))) {
					errors.rejectValue("startDate", "할인 시작 시간이 할인 끝시간 보다 클 수 없습니다.");
				}
			}
		}
	}
}
