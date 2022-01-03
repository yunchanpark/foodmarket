package com.lec.foodmarket.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.lec.foodmarket.domain.Cart;
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
	@GetMapping("/searchList")
	public String productSearchList(@RequestParam("sword") String sword, Model model) {
		List<Product> list = productService.productSwordSelect(sword);
		model.addAttribute("category", productService.productCategorySelect());
		model.addAttribute("list", list);
		return "layout/user/product/list";
	}
	
	// 카테고리 검색
	@GetMapping("/list/category/{category}")
	public String producCategorytList(@PathVariable("category") Long category, Model model) {
		List<Product> list = productService.productByCategoryNo(category);
		model.addAttribute("category", productService.productCategorySelect());
		model.addAttribute("list", list);
		return "layout/user/product/list";
	}
	
	// New, BEST 등의 카테고리 검색
	@GetMapping("/list/categoryList/{category}")
	public String productCategorySortList(@PathVariable("category") Long category, Model model) {
		List<Product> list = productService.productCategory(category);
		model.addAttribute("category", productService.productCategorySelect());
		model.addAttribute("list", list);
		return "layout/user/product/list";
	}
	
	// 상품 상세 조회
	@GetMapping("/view/{productNo}")
	public String productView(@PathVariable("productNo") Long productNo, Model model) {
		Product dto = productService.viewByProductNo(productNo);
		model.addAttribute("category", productService.productCategorySelect());
		model.addAttribute("dto", dto);
		return "layout/user/product/view";
	}
	
	// 상품 좋아요 등록
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
	
	// 상품 좋아요 상태
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
	
	@GetMapping("/cartList")
	public void memberCart(Principal principal, Model model, HttpSession session) {
		List<Cart> cartList = new ArrayList<Cart>();
		if (principal != null) cartList = productService.memberCart(principal.getName());
		model.addAttribute("cartList", cartList);
	}
	
	/******************************************
	 * POST 방식
	 ******************************************/
	@ResponseBody
	@PostMapping("/cart")
	public void productCartInsert(@RequestParam("productNo") Long productNo, @RequestParam("cnt") int cnt, Principal principal) {
		if(principal != null) {
			productService.productAndMemberCart(productNo, principal.getName(), cnt);
		}
	}
	
	@ResponseBody
	@PostMapping("/cartUpdate")
	public JsonObject productCartUpdate(@RequestParam("productNo") Long productNo, @RequestParam("cnt") int cnt, Principal principal) {
		Cart cart = null;
		if(principal != null) {
			productService.memberUpdate(principal.getName(), productNo, cnt);
			cart = productService.memberAndProductNo(principal.getName(), productNo).orElse(cart);
		}
		
		JsonObject data = new JsonObject();
		data.addProperty("cartNo", cart.getCartNo());
		data.addProperty("productNo", cart.getProductNo().getProductNo());
		data.addProperty("categoryNo", cart.getProductNo().getCategoryNo().getCategoryNo());
		data.addProperty("name", cart.getProductNo().getName());
		data.addProperty("description", (cart.getProductNo().getDescription() == null ? "" : cart.getProductNo().getDescription()));
		data.addProperty("price", cart.getProductNo().getPrice());
		data.addProperty("purchasePrice", cart.getProductNo().getPurchasePrice());
		data.addProperty("stock", cart.getProductNo().getStock());
		data.addProperty("discount", (cart.getProductNo().getDiscount() == 0 ? 0 : cart.getProductNo().getDiscount()));
		data.addProperty("exchangeRate", (cart.getProductNo().getExchangeRate() == null ? "" : cart.getProductNo().getExchangeRate()));
		data.addProperty("detailContent", (cart.getProductNo().getDetailContent() == null ? "" : cart.getProductNo().getDetailContent()));
		data.addProperty("startDate", (cart.getProductNo().getDisStart() == null ? "" : cart.getProductNo().getDisStart().toString()));
		data.addProperty("endDate", (cart.getProductNo().getDisEnd() == null ? "" : cart.getProductNo().getDisEnd().toString()));
		data.addProperty("orginName", cart.getProductNo().getImageOrgin());
		data.addProperty("saveName", cart.getProductNo().getImageSave());
		data.addProperty("cnt", cart.getCnt());
		
		return data;
	}
	
	@ResponseBody
	@PostMapping("/cartAllDelete")
	public int delete (@RequestParam("cartNoArr") List<Long> cartNoArr, Principal principal) {
		int result = 0;
		cartNoArr.forEach(m -> System.out.println(m));
		if(principal != null) {
			productService.deleteAllCart(cartNoArr);
		}
		
		return result;
	}
	
	@ResponseBody
	@PostMapping("/cartDelete")
	public int delete(@RequestParam("cartNo") Long cartNo, Principal principal) {
		int result = 0;
		if(principal != null) {
			result = productService.deleteCart(cartNo);
		}
		return result;
	}
}













