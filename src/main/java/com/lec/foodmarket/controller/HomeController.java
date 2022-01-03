package com.lec.foodmarket.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lec.foodmarket.domain.Cart;
import com.lec.foodmarket.domain.Product;
import com.lec.foodmarket.domain.ProductCategory;
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
		
		model.addAttribute("category", productService.productCategorySelect());
		model.addAttribute("newList", newList);
		model.addAttribute("bestList", bestList);
	}
	
	@ResponseBody
	@GetMapping("/cart")
	public JsonObject categoryAndCartCnt(Principal principal) {
		JsonObject json = new JsonObject();
		
		List<Cart> list= new ArrayList<Cart>();
		if(principal != null) list = productService.memberCart(principal.getName());
		json.addProperty("cnt", list != null ? list.size() : 0 );

		return json;
	}
}


















