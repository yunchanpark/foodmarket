package com.lec.foodmarket.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lec.foodmarket.commond.FileUpload;
import com.lec.foodmarket.domain.Product;
import com.lec.foodmarket.domain.dto.FileUploadDTO;
import com.lec.foodmarket.domain.dto.ProductDTO;
import com.lec.foodmarket.service.ProductService;
import com.lec.foodmarket.validator.ProductValidator;

@Controller
@RequestMapping("/layout/admin/product")
public class AdminProductController {

	private ProductService productService;

	@Autowired
	public AdminProductController(ProductService productService) {
		this.productService = productService;
	}

	public AdminProductController() {
		;
	}

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
	public void productList() {
	}

	/******************************************
	 * POST 방식
	 ******************************************/
	// 상품 등록
	@PostMapping("/write")
	public String productWrite(@ModelAttribute("product") @Valid ProductDTO productDTO, BindingResult result,
			@RequestParam("image") MultipartFile upload, Model model, RedirectAttributes redirectAttrs)
			throws Exception {

		System.out.println(productDTO);
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
		Product product = Product.builder()
				.categoryNo(productDTO.getCategoryNo())
				.name(productDTO.getName())
				.description(productDTO.getDescription())
				.price(productDTO.getPrice())
				.purchasePrice(productDTO.getPurchasePrice())
				.stock(productDTO.getStock())
				.imageOrgin(dto.getOrginName())
				.imageSave(dto.getSaveName())
				.discount(productDTO.getDiscount())
				.exchangeRate(productDTO.getExchangeRate())
				.detailContent(productDTO.getDetailContent())
				.disStart(productDTO.getStartDate())
				.disEnd(productDTO.getEndDate())
				.build();
		productService.productSave(product);
		return "redirect:/layout/admin/product/list";
	}

	// 유효성 binding
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new ProductValidator());
	}
}
