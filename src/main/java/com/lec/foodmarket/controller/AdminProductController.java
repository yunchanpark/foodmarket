package com.lec.foodmarket.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lec.foodmarket.commond.FileUpload;
import com.lec.foodmarket.domain.Product;
import com.lec.foodmarket.domain.dto.FileUploadDTO;
import com.lec.foodmarket.domain.dto.ProductDTO;
import com.lec.foodmarket.domain.dto.SearchDTO;
import com.lec.foodmarket.service.ProductService;
import com.lec.foodmarket.validator.ProductValidator;
import com.lec.foodmarket.validator.SearchValidator;

@Controller
@RequestMapping("/layout/admin/product")
public class AdminProductController {

	// 상품 서비스 부분
	private ProductService productService;
	
	// 상품 등록 유효성
	private ProductValidator productValidator;
	
	// 상품 검색 유효성
	private SearchValidator searchValidator;

	@Autowired
	public AdminProductController(ProductService productService, ProductValidator productValidator, SearchValidator searchValidator) {
		this.productService = productService;
		this.productValidator = productValidator;
		this.searchValidator = searchValidator;
	}

	public AdminProductController() {;}

	/******************************************
	 * 관리자 상품(등록, 조회, 수정, 삭제) 엑셀을 이용한 일괄 등록(이미지 어떻게 할까 생각해야됨) 상세설명(에디터 사용 Text타입으로
	 * 등록) 추가사항이나 수정사항 알아서
	 ******************************************/

	/******************************************
	 * GET 방식
	 ******************************************/
	// 상품 등록
	@GetMapping("/write")
	public void productWrite(@ModelAttribute("product") ProductDTO productDTO, Model model) {
		model.addAttribute("category", productService.productCategorySelect());
	}

	// 상품 목록
	@GetMapping("/list")
	public String productList(@Valid SearchDTO searchDTO, BindingResult result,
			Model model, RedirectAttributes redirectAttrs) {
		// 상품 검색 유효성 바인딩
		searchValidator.validate(searchDTO, result);
		
		if(result.hasErrors()) {
			System.out.println(searchDTO);
			if (result.getFieldError("searchKeyword") != null)
				redirectAttrs.addFlashAttribute("errSearchKeyword", result.getFieldError("searchKeyword").getCode());
			if (result.getFieldError("selectStartDate") != null)
				redirectAttrs.addFlashAttribute("errSelectStartDate", result.getFieldError("selectStartDate").getCode());
			if (result.getFieldError("selectEndDate") != null)
				redirectAttrs.addFlashAttribute("errSelectEndDate", result.getFieldError("selectEndDate").getCode());
			redirectAttrs.addFlashAttribute("search", searchDTO);
			return "redirect:/layout/admin/product/list";
		}
		
		String keyword = searchDTO.getKeyword();
		String searchKeyword = searchDTO.getSearchKeyword();
		LocalDate selectStartDate = searchDTO.getSelectStartDate();
		LocalDate selectEndDate = searchDTO.getSelectEndDate();
		LocalDateTime start;
		LocalDateTime end;
		
		List<Product> list = new ArrayList<Product>();
		Product product;
		
		// 검색 버튼만 눌렀을 때
		if(keyword != null && (searchKeyword == null || searchKeyword.trim().length() == 0) && selectStartDate == null && selectEndDate == null) {
			list = productService.productAllSelecet();
		}
		// 검색 카테고리와 검색 키워드만 입력했을 때
		else if (keyword != null && (searchKeyword != null || searchKeyword.trim().length() == 0) && selectStartDate == null && selectEndDate == null) {
			// 검색 카테고리가 상품명일 때
			if(keyword.equals("name")) list = productService.productNameSelecet(searchKeyword);
			// 검색 카테고리가 추가 상품명일 때
			if(keyword.equals("description")) list = productService.productDescriptionSelect(searchKeyword);
			// 검색 카테고리가 상품번호일 때
			if(keyword.equals("productNo")) {
				product = productService.productProductNoSelect(Long.parseLong(searchKeyword)).orElse(null);
				list.add(product);
			}
		} 
		// 검색 카테고리와 날짜를 같이 입력했을 때
		else if (keyword != null && (searchKeyword == null || searchKeyword.trim().length() == 0) && selectStartDate != null && selectEndDate != null) {
			start = selectStartDate.atTime(0, 0, 0);
			end = selectEndDate.atTime(23, 59, 59);
			list = productService.productUpdateAtSelect(start, end);
		}
		else if (keyword != null && (searchKeyword != null || searchKeyword.trim().length() != 0) && selectStartDate != null && selectEndDate != null) {
			start = selectStartDate.atTime(0, 0, 0);
			end = selectEndDate.atTime(23, 59, 59);
			if(keyword.equals("name")) list = productService.productUpdateAtAndName(searchKeyword, start, end);
			if(keyword.equals("description")) list = productService.productUpdateAtAndDescription(searchKeyword, start, end);
			if(keyword.equals("productNo")) list = productService.productUpdateAtAndProductNo(Long.parseLong(searchKeyword), start, end);
		}
		
		model.addAttribute("search", searchDTO);
		model.addAttribute("list",list);
		
		return "layout/admin/product/list";
	}

	/******************************************
	 * POST 방식
	 ******************************************/
	// 상품 등록
	@PostMapping("/write")
	public String productWrite(@ModelAttribute("product") @Valid ProductDTO productDTO, BindingResult result,
			@RequestParam("image") MultipartFile upload, Model model, RedirectAttributes redirectAttrs)
			throws Exception {
		// 상품 등록 유효성 검사 바인딩
		productValidator.validate(productDTO, result);
		if (upload.getSize() == 0 || result.hasErrors()) {
			if (result.getFieldError("categoryNo") != null)
				redirectAttrs.addFlashAttribute("errCategoryNo", result.getFieldError("categoryNo").getCode());
			if (result.getFieldError("name") != null)
				redirectAttrs.addFlashAttribute("errProductname", result.getFieldError("name").getCode());
			if (result.getFieldError("price") != null)
				redirectAttrs.addFlashAttribute("errPrice", result.getFieldError("price").getCode());
			if (result.getFieldError("purchasePrice") != null)
				redirectAttrs.addFlashAttribute("errPurchasePrice", result.getFieldError("purchasePrice").getCode());
			if (result.getFieldError("stock") != null)
				redirectAttrs.addFlashAttribute("errStock", result.getFieldError("stock").getCode());
			if (result.getFieldError("discount") != null)
				redirectAttrs.addFlashAttribute("errDiscount", result.getFieldError("discount").getCode());
			if (result.getFieldError("startDate") != null)
				redirectAttrs.addFlashAttribute("errDiscountTime", result.getFieldError("startDate").getCode());

			redirectAttrs.addFlashAttribute("errImage", "이미지를 다시 선택해주세요");
			redirectAttrs.addFlashAttribute("result", 0);
			redirectAttrs.addFlashAttribute("product", productDTO);
			return "redirect:/layout/admin/product/write";
		}

		// 상품 이미지 업로드
		FileUpload file = new FileUpload();
		FileUploadDTO dto = file.ckUpload("/productImages/product/", "productImages\\product", upload);
		
		// 상품 값 세팅
		Product product = Product.builder().categoryNo(productDTO.getCategoryNo()).name(productDTO.getName())
				.description(productDTO.getDescription()).price(productDTO.getPrice())
				.purchasePrice(productDTO.getPurchasePrice()).stock(productDTO.getStock())
				.imageOrgin(dto.getOrginName()).imageSave(dto.getSaveName())
				.discount(productDTO.getDiscount() == null ? 0 : productDTO.getDiscount())
				.exchangeRate(productDTO.getExchangeRate()).detailContent(productDTO.getDetailContent())
				.disStart(productDTO.getStartDate()).disEnd(productDTO.getEndDate()).build();
		productService.productSave(product);
		return "redirect:/layout/admin/product/list";
	}
	
	// 여러 상품 삭제
	@ResponseBody
	@PostMapping(value ="/delete", produces = "application/json")
	public void productSelectDelete(@RequestParam("productNoArr") List<Long> arrStringProductNo) throws Exception {
		int result = productService.deleteByimage(arrStringProductNo, "productImages\\product");
		if(result == 1) productService.productDeleteInBatch(arrStringProductNo);
	}
}


























