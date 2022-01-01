package com.lec.foodmarket.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.lec.foodmarket.domain.Member;
import com.lec.foodmarket.domain.Product;
import com.lec.foodmarket.service.ProductService;

@Controller
@RequestMapping("/layout/user/product")
public class ProductController {

	private ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	public ProductController() {;}
	
	/******************************************
	 * 사용자
	 *   상품 전체조회, 카테고리조회, 검색조회
	 *   장바구니
	 * 추가사항이나 수정사항 알아서   
	 ******************************************/
	
	/******************************************
	 * GET 방식
	 ******************************************/
	@GetMapping("/list/category/{category}")
	public String producCategorytList(@PathVariable("category") Long category, Model model) {
		List<Product> list = productService.productByCategoryNo(category);
		model.addAttribute("list", list);
		return "layout/user/product/list";
	}
	
	@GetMapping("/list/categoryList/{category}")
	public String productCategorySortList(@PathVariable("category") Long category, Model model) {
		List<Product> list = productService.productCategory(category);
		model.addAttribute("list", list);
		return "layout/user/product/list";
	}
	
	@GetMapping("/view/{productNo}")
	public String productView(@PathVariable("productNo") Long productNo, Model model) {
		Product dto = productService.viewByProductNo(productNo);
		model.addAttribute("dto", dto);
		return "layout/user/product/view";
	}
	
	@ResponseBody
	@GetMapping("/view/like/{productNo}")
	public JsonObject productAndMemberLike(@PathVariable("productNo") Long productNo, Principal principal) {
		JsonObject json = new JsonObject();
		
		boolean ck = false;
		
		if (principal != null) {
			ck = productService.addOrDeletLike(principal.getName(), productNo);
		}
		Integer likeCnt = productService.productLikeCnt(productNo);
		
		json.addProperty("ck", ck);
		json.addProperty("likeCnt", likeCnt);
		
		// 성공
		return json;
	}
	
	@ResponseBody
	@GetMapping("/view/likeCk/{productNo}")
	public JsonObject productAndMemberLikeCk(@PathVariable("productNo") Long productNo, Principal principal) {
		JsonObject json = new JsonObject();
		
		boolean ck = false;
		
		if (principal != null) {
			ck = productService.productAndMember(principal.getName(), productNo);
		}
		Integer likeCnt = productService.productLikeCnt(productNo);
		
		json.addProperty("ck", ck);
		json.addProperty("likeCnt", likeCnt);
		
		// 성공
		return json;
	}
	/******************************************
	 * POST 방식
	 ******************************************/
}











