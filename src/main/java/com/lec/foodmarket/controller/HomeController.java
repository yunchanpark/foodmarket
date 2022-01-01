package com.lec.foodmarket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lec.foodmarket.domain.Product;
import com.lec.foodmarket.service.ProductService;

@Controller
@RequestMapping("/layout")
public class HomeController {
	
	// 상품 서비스 부분
	private ProductService productService;
	
	@Autowired
	public HomeController(ProductService productService) {
		this.productService = productService;
	}

	public HomeController() {;}
	
	/******************************************
	 * GET 방식
	 ******************************************/
	
	@GetMapping("/admin/index")
	public void adminHome() {
		//TODO
	}

	@GetMapping("/user/index")
	public void userHome(Model model) {
		List<Product> newList = null;
		List<Product> bestList = null;
		
		newList = productService.productTop11ByCreatedAtOrderByDesc();
		bestList = productService.productTop3ByViewCntOrderByDesc();
		
		model.addAttribute("newList", newList);
		model.addAttribute("bestList", bestList);
	}
}
