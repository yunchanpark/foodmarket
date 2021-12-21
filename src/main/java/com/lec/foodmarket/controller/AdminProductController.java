package com.lec.foodmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lec.foodmarket.domain.Product;
import com.lec.foodmarket.service.ProductService;

@Controller
@RequestMapping("/layout/admin/product")
public class AdminProductController {

	private ProductService productService;

	@Autowired
	public AdminProductController(ProductService productService) {
		this.productService = productService;
	}
	
	public AdminProductController() {;}
	
	/******************************************
	 * 관리자
	 *   상품(등록, 조회, 수정, 삭제)
	 *   엑셀을 이용한 일괄 등록(이미지 어떻게 할까 생각해야됨)
	 *   상세설명(에디터 사용 Text타입으로 등록)
	 * 추가사항이나 수정사항 알아서   
	 ******************************************/
	
	/******************************************
	 * GET 방식
	 ******************************************/
	@GetMapping("/write")
	public void productWrite(Model model) {
		model.addAttribute("category", productService.productCategorySelect());
	}
	
	
	
	
	/******************************************
	 * POST 방식
	 ******************************************/
	@PostMapping("/write")
	public void productWrite(Product product) {
		System.out.println(123123);
		System.out.println(product);
	}
	
	
	
}











