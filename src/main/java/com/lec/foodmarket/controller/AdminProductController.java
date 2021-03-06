package com.lec.foodmarket.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lec.foodmarket.commond.FileUpload;
import com.lec.foodmarket.domain.Product;
import com.lec.foodmarket.domain.dto.FileUploadDTO;
import com.lec.foodmarket.domain.dto.ProductDTO;
import com.lec.foodmarket.domain.dto.SearchDTO;
import com.lec.foodmarket.service.ExcelService;
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
	
	// 엑셀로 일괄 등록
	private ExcelService excel;
	
	@Autowired
	public AdminProductController(ProductService productService, ProductValidator productValidator, SearchValidator searchValidator, ExcelService excel) {
		this.productService = productService;
		this.productValidator = productValidator;
		this.searchValidator = searchValidator;
		this.excel = excel;
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

	// 상품 수정
	@GetMapping("/update/{productNo}")
	public String productUpdate(ProductDTO productDTO, @PathVariable("productNo") Long productNo
			, Model model) {
		Product product = productService.productProductNoSelect(productNo).orElse(null);
		productDTO = ProductDTO.builder()
				.categoryNo(product.getCategoryNo())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.purchasePrice(product.getPurchasePrice())
				.stock(product.getStock())
				.discount(product.getDiscount())
				.exchangeRate(product.getExchangeRate())
				.detailContent(product.getDetailContent())
				.startDate(product.getDisStart())
				.endDate(product.getDisEnd())
				.discountCk(product.getDiscount() == 0 ? "N" : "Y")
				.duringCheck(product.getDisStart() != null && product.getDisEnd() != null ? "Y":"")
				.build();

		model.addAttribute("category", productService.productCategorySelect());
		model.addAttribute("productNo", productNo);
		model.addAttribute("product", productDTO);
		model.addAttribute("result", 0);
		
		return "layout/admin/product/update";
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

		List<Product> list = productService.productSelecet(searchDTO);
		
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
		Product product = Product.builder()
				.categoryNo(productDTO.getCategoryNo())
				.name(productDTO.getName())
				.description(productDTO.getDescription())
				.price(productDTO.getPrice())
				.purchasePrice(productDTO.getPurchasePrice())
				.stock(productDTO.getStock())
				.imageOrgin(dto.getOrginName())
				.imageSave(dto.getSaveName())
				.discount(productDTO.getDiscount() == null ? 0 : productDTO.getDiscount())
				.exchangeRate(productDTO.getExchangeRate())
				.detailContent(productDTO.getDetailContent())
				.disStart(productDTO.getStartDate())
				.disEnd(productDTO.getEndDate())
				.build();
		productService.productSave(product);
		return "redirect:/layout/admin/product/list";
	}
	
	@PostMapping("writeAll")
	public String productAllWrite(@Valid ProductDTO productDTO) {
		for(ProductDTO dto : productDTO.getProductList()) System.out.println(dto);
		productService.productAllSave(productDTO.getProductList());
		return "redirect:/layout/admin/product/list";
	}
	
	// 상품 이미지 일괄 등록
	@ResponseBody
	@PostMapping("/writeImageAll")
	public void productWriteAll(HttpServletResponse response, MultipartHttpServletRequest upload) throws Exception {
		// 상품 다중 이미지 업로드
		FileUpload file = new FileUpload();
		List<FileUploadDTO> list = file.ckAllUpload("/productImages/product/", "productImages\\product", upload);

		excel.downloadProductInfo(response, list);
	}
	
	// 상품 엑셀 파일 읽기
	@ResponseBody
	@PostMapping("/excel")
	public JsonObject productExcel(@RequestParam("excelImage") MultipartFile excelFile) throws Exception {
		Map<ProductDTO, Integer> excelList = excel.uploadExcelFile(excelFile);
		
		int succes = 0;
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("total", excelList.size());		
		
		JsonArray array = new JsonArray();
		
		for( Map.Entry<ProductDTO, Integer> elem : excelList.entrySet() ){
			JsonObject data = new JsonObject();
			ProductDTO product = elem.getKey();
			if(elem.getValue() != 1) {
				data.addProperty("categoryNo", product.getCategoryNo().getCategoryNo());
				data.addProperty("name", product.getName());
				data.addProperty("description", (product.getDescription() == null ? "" : product.getDescription()));
				data.addProperty("price", product.getPrice());
				data.addProperty("purchasePrice", product.getPurchasePrice());
				data.addProperty("stock", product.getStock());
				data.addProperty("discount", (product.getDiscount() == null ? 0 : product.getDiscount()));
				data.addProperty("exchangeRate", (product.getExchangeRate() == null ? "" : product.getExchangeRate()));
				data.addProperty("detailContent", (product.getDetailContent() == null ? "" : product.getDetailContent()));
				data.addProperty("startDate", (product.getStartDate() == null ? "" : product.getStartDate().toString()));
				data.addProperty("endDate", (product.getEndDate() == null ? "" : product.getEndDate().toString()));
				data.addProperty("orginName", product.getOrginName());
				data.addProperty("saveName", product.getSaveName());
				data.addProperty("discountCk", (product.getDiscountCk() == null ? "N" : product.getDiscountCk()));
				data.addProperty("duringCheck", (product.getDuringCheck() == null ? "N" : product.getDuringCheck()));
				array.add(data);
				succes++;
			} 
		}
		
		jsonObject.addProperty("fail", (excelList.size() - succes));
		jsonObject.addProperty("succes", (succes));
		jsonObject.add("data", array);

		return jsonObject;
	}

	// 상품 수정
	@PostMapping("/update")
	public String productUpdate(@ModelAttribute("product") @Valid ProductDTO productDTO, BindingResult result,
			@RequestParam(value = "image", required = false) MultipartFile upload, Long productNo, Model model, RedirectAttributes redirectAttrs)
					throws Exception {
		// 상품 등록 유효성 검사 바인딩
		productValidator.validate(productDTO, result);
		if (result.hasErrors()) {
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
			
			redirectAttrs.addFlashAttribute("result", 0);
			redirectAttrs.addFlashAttribute("productRedirect", productDTO);
			return "redirect:/layout/admin/product/update/" + productNo;
		}

		Product product = productService.productProductNoSelect(productNo).orElse(null);
		
		// 상품 값 세팅
		product.setCategoryNo(productDTO.getCategoryNo());
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		product.setPurchasePrice(productDTO.getPurchasePrice());
		product.setStock(productDTO.getStock());
		product.setDiscount(productDTO.getDiscount() == null ? 0 : productDTO.getDiscount());
		product.setExchangeRate(productDTO.getExchangeRate());
		product.setDetailContent(productDTO.getDetailContent());
		product.setDisStart(productDTO.getStartDate());
		product.setDisEnd(productDTO.getEndDate());
		
		// 상품 이미지 업로드가 수정 되었을 때
		if(upload.getSize() != 0) {
			FileUpload file = new FileUpload();
			FileUploadDTO dto = file.ckUpload("/productImages/product/", "productImages\\product", upload);
			product.setImageOrgin(dto.getOrginName());
			product.setImageSave(dto.getSaveName());
		} else {
			product.setImageOrgin(product.getImageOrgin());
			product.setImageSave(product.getImageSave());
		}

		System.out.println(product);
		productService.productSave(product);
		return "redirect:/layout/admin/product/list";
	}
	
	// 여러 상품 삭제
	@ResponseBody
	@PostMapping(value ="/delete", produces = "application/json")
	public int productSelectDelete(@RequestParam("productNoArr") List<Long> arrStringProductNo) throws Exception {
		int success = 0;
		int result = productService.deleteByimage(arrStringProductNo, "productImages\\product");
		if(result == 1) {
			success = productService.productDeleteInBatch(arrStringProductNo);
		}
		return success;
	}
}


























