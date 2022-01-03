package com.lec.foodmarket.service;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lec.foodmarket.domain.Cart;
import com.lec.foodmarket.domain.Member;
import com.lec.foodmarket.domain.Product;
import com.lec.foodmarket.domain.ProductCategory;
import com.lec.foodmarket.domain.ProductLikeCnt;
import com.lec.foodmarket.domain.dto.ProductDTO;
import com.lec.foodmarket.domain.dto.SearchDTO;
import com.lec.foodmarket.repository.CartRepository;
import com.lec.foodmarket.repository.MemberRepository;
import com.lec.foodmarket.repository.ProductCategoryRepository;
import com.lec.foodmarket.repository.ProductLikeCntRepository;
import com.lec.foodmarket.repository.ProductRepository;

@Service
public class ProductService {

	private ProductRepository productRepository; // 상품
	private ProductCategoryRepository productCategoryRepository; // 상품 카테고리
	private ProductLikeCntRepository productLikeCntRepository; // 상품 좋아요
	private MemberRepository memberRepository;
	private CartRepository cartRepository; // 장바구니

	@Autowired
	public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository,
			ProductLikeCntRepository productLikeCntRepository, MemberRepository memberRepository,
			CartRepository cartRepository) {
		this.productRepository = productRepository;
		this.productCategoryRepository = productCategoryRepository;
		this.productLikeCntRepository = productLikeCntRepository;
		this.memberRepository = memberRepository;
		this.cartRepository = cartRepository;
	}

	public ProductService() {
		;
	}

	/******************************************
	 * 관리자 상품(등록, 조회, 수정, 삭제) 엑셀을 이용한 일괄 등록(이미지 어떻게 할까 생각해야됨) 상세설명(에디터 사용 Text타입으로
	 * 등록)
	 * 
	 * 사용자 상품 전체조회, 카테고리조회, 검색조회 장바구니
	 * 
	 * 추가사항이나 수정사항 알아서
	 ******************************************/

	/******************************************
	 * 관리자
	 ******************************************/
	// 상품 일괄 등록
	public void productAllSave(List<ProductDTO> list) {
		List<Product> productList = new ArrayList<Product>();
		for (ProductDTO dto : list) {
			Product product = new Product();
			product.setCategoryNo(dto.getCategoryNo());
			product.setName(dto.getName());
			product.setDescription(dto.getDescription());
			product.setPrice(dto.getPrice());
			product.setPurchasePrice(dto.getPurchasePrice());
			product.setStock(dto.getStock());
			product.setImageOrgin(dto.getOrginName());
			product.setImageSave(dto.getSaveName());
			product.setDiscount(dto.getDiscount());
			product.setExchangeRate(dto.getExchangeRate());
			product.setDetailContent(dto.getDetailContent());
			product.setDisStart(dto.getStartDate());
			product.setDisEnd(dto.getEndDate());

			productList.add(product);
		}

		productRepository.saveAll(productList);
	}

	// 상품 카테고리 조회
	public List<ProductCategory> productCategorySelect() {
		return productCategoryRepository.findAll();
	}

	// 카테고리 이름 조회
	public ProductCategory productCategoryByName(String name) {
		return productCategoryRepository.findByName(name).orElse(null);
	}

	// 카테고리 이름 조회
	public ProductCategory productCategoryById(Long id) {
		return productCategoryRepository.findById(id).orElse(null);
	}

	// 상품 등록
	public void productSave(Product product) {
		productRepository.save(product);
	}

	// 상품 검색
	public List<Product> productSelecet(SearchDTO searchDTO) {
		String keyword = searchDTO.getKeyword();
		String searchKeyword = searchDTO.getSearchKeyword();
		LocalDate selectStartDate = searchDTO.getSelectStartDate();
		LocalDate selectEndDate = searchDTO.getSelectEndDate();
		LocalDateTime start;
		LocalDateTime end;

		List<Product> list = new ArrayList<Product>();
		Product product;

		// 검색 버튼만 눌렀을 때
		if (keyword == null && (searchKeyword == null || searchKeyword.trim().length() == 0) && selectStartDate == null
				&& selectEndDate == null) {
			list = productRepository.findAll();
		}
		// 검색 카테고리와 검색 키워드만 입력했을 때
		else if (keyword != null && (searchKeyword != null || searchKeyword.trim().length() == 0)
				&& selectStartDate == null && selectEndDate == null) {
			// 검색 카테고리가 상품명일 때
			if (keyword.equals("name"))
				list = productRepository.findByNameContainsIgnoreCase(searchKeyword);
			// 검색 카테고리가 추가 상품명일 때
			if (keyword.equals("description"))
				list = productRepository.findByDescriptionContainsIgnoreCase(searchKeyword);
			// 검색 카테고리가 상품번호일 때
			if (keyword.equals("productNo")) {
				product = productRepository.findById(Long.parseLong(searchKeyword)).orElse(null);
				if (product != null)
					list.add(product);
			}
		}
		// 검색 카테고리와 날짜를 같이 입력했을 때
		else if (keyword != null && (searchKeyword == null || searchKeyword.trim().length() == 0)
				&& selectStartDate != null && selectEndDate != null) {
			start = selectStartDate.atTime(0, 0, 0);
			end = selectEndDate.atTime(23, 59, 59);
			list = productRepository.findByUpdatedAtBetween(start, end);
		} else if (keyword != null && (searchKeyword != null || searchKeyword.trim().length() != 0)
				&& selectStartDate != null && selectEndDate != null) {
			start = selectStartDate.atTime(0, 0, 0);
			end = selectEndDate.atTime(23, 59, 59);
			if (keyword.equals("name"))
				list = productRepository.findByNameContainsIgnoreCaseAndUpdatedAtBetween(searchKeyword, start, end);
			if (keyword.equals("description"))
				list = productRepository.findByDescriptionContainsIgnoreCaseAndUpdatedAtBetween(searchKeyword, start,
						end);
			if (keyword.equals("productNo"))
				list = productRepository.findByProductNoAndUpdatedAtBetween(Long.parseLong(searchKeyword), start, end);
		}
		return list;
	}
	
	public List<Product> productSwordSelect(String sword) {
		return productRepository.findByNameContainsIgnoreCase(sword);
	}

	// 상품번호로 상품 검색
	public Optional<Product> productProductNoSelect(Long productNo) {
		return productRepository.findById(productNo);
	}

	// 일괄 삭제
	public void productDeleteInBatch(List<Long> productIds) {
		productRepository.deleteAllByIdInBatch(productIds);
	}

	// 첨부파일 삭제
	public int deleteByimage(List<Long> product_no, String realPath) {
		int result = 0;
		// 물리적인 경로
		String ckUploadPath = "C:\\spring_foodmarket\\ckupload\\" + realPath;
		List<Product> productList = productRepository.findAllById(product_no);
		for (Product dto : productList) {
			File f = new File(ckUploadPath, dto.getImageSave());
			if (f.exists()) {
				if (f.delete()) {
					result = 1;
				} else {
					result = 0;
				}
			} else {
				result = -1;
			}
		}
		return result;
	}

	/******************************************
	 * 사용자
	 ******************************************/
	// 최근 등록일 순 Top11
	public List<Product> productTop11ByCreatedAtOrderByDesc() {
		return productRepository.findTop11OrderByCreatedAt();
	}

	// 최근 조회순 Top3
	public List<Product> productTop3ByViewCntOrderByDesc() {
		return productRepository.findTop3OrderByViewCnt();
	}

	// 카테고리별 검색
	public List<Product> productByCategoryNo(Long categoryNo) {
		return productRepository.findByCategoryNo(productCategoryRepository.findById(categoryNo).get());
	}

	// 카테고리별 검색
	public List<Product> productCategory(Long category) {
		List<Product> list = null;

		if (category == 1) { // 최근 등록일 순
			list = productRepository.findAll(Sort.by(Direction.DESC, "createdAt"));
		} else if (category == 2) { // 초회수 순
			list = productRepository.findAll(Sort.by(Direction.DESC, "viewCnt"));
		} else if (category == 3) { // 좋아요 순
			list = productRepository.findAll(Sort.by(Direction.DESC, "likeCnt"));
		} else if (category == 4) { // 가격 낮은 순
			list = productRepository.findAll(Sort.by(Direction.ASC, "price"));
		} else if (category == 5) { // 가격 높은 순
			list = productRepository.findAll(Sort.by(Direction.DESC, "price"));
		}

		return list;
	}

	// 특정 상품 읽어오기 + 조회수 증가
	@Transactional
	public Product viewByProductNo(Long productNo) {
		Product product = productRepository.findById(productNo).orElse(null);

		if (product != null) {
			product.setViewCnt(product.getViewCnt() + 1);
			productRepository.saveAndFlush(product);
		}
		return product;
	}

	// 상품 좋아요 추가 및 삭제
	@Transactional
	public boolean addOrDeletLike(String id, Long productNo) {
		Member member = memberRepository.findById(id).orElse(null);
		Product product = productRepository.findById(productNo).orElse(null);

		// 중복 좋아요 방지
		if (isNotAlreadyLike(member, product)) {
			productLikeCntRepository.save(new ProductLikeCnt(member, product));
			product.setLikeCnt(product.getLikeCnt() + 1);
			productRepository.saveAndFlush(product);
			return true;
		} else {
			productLikeCntRepository.deleteByMemberAndProduct(member, product);
			product.setLikeCnt(product.getLikeCnt() - 1);
			productRepository.saveAndFlush(product);
			return false;
		}
	}

	// 회원 상품 객체 찾기
	public boolean productAndMember(String id, Long productNo) {
		Member member = memberRepository.findById(id).orElse(null);
		Product product = productRepository.findById(productNo).orElse(null);
		return !(isNotAlreadyLike(member, product));
	}

	// 사용자가 이미 좋아요 한 게시물인지 체크
	public boolean isNotAlreadyLike(Member member, Product product) {
		return productLikeCntRepository.findByMemberAndProduct(member, product).isEmpty();
	}

	// 특정 상품 좋아요 개수
	public int productLikeCnt(Long productNo) {
		Product product = productRepository.findById(productNo).orElse(null);
		return productLikeCntRepository.countByProduct(product);
	}

	// 특정 아이디 상품 장바구니 등록 및 수정
	@Transactional
	public void productAndMemberCart(Long productNo, String id, int cnt) {
		Member member = memberRepository.findById(id).orElse(null);
		Product product = productRepository.findById(productNo).orElse(null);
		
		Optional<Cart> cart = isNotCart(member, product);
		Cart cartDTO;
		
		if (cart.isEmpty()) {
			cartDTO = Cart.builder()
					.id(member)
					.productNo(product)
					.cnt(cnt)
					.build();
			cartRepository.save(cartDTO);
		} else {
			cartDTO = cart.get();
			cartDTO.setCnt(cart.get().getCnt() + cnt);
			cartRepository.saveAndFlush(cartDTO);
		}
	}
	
	// 아이디 상품 번호
	@Transactional
	public Optional<Cart> memberAndProductNo(String id, Long productNo) {
		Member member = memberRepository.findById(id).orElse(null);
		Product product = productRepository.findById(productNo).orElse(null);
		return isNotCart(member, product);
	}

	// 특정 아이디 특정 상품 장바구니 찾기
	public Optional<Cart> isNotCart(Member member, Product product) {
		return cartRepository.findByIdAndProductNo(member, product);
	}
	
	// 특정 아이디 장바구니 찾기
	public List<Cart> memberCart(String id) {
		Member member = memberRepository.findById(id).get();
		return cartRepository.findAllById(member);
	}
	
	// 특정 아이디 상품 업데이트
	@Transactional
	public void memberUpdate(String id, Long productNo, int cnt) {
		Member member = memberRepository.findById(id).orElse(null);
		Product product = productRepository.findById(productNo).orElse(null);
		Optional<Cart> cart = isNotCart(member, product);
		
		if (!(cart.isEmpty())) {
			cart.get().setCnt(cart.get().getCnt() + cnt);
			cartRepository.saveAndFlush(cart.get());
		}
	}
	
	// 특정 아이디 상품 찾기
	@Transactional
	public List<Long> selectMemberAndProduct(List<Long> productNos, String id) {
		List<Long> cartIds = new ArrayList<Long>();
		List<Product> products = productRepository.findAllById(productNos);
		Member member = memberRepository.findById(id).orElse(null);
		List<Cart> cart = cartRepository.findMemberAndProductNoIn(member, products);
		for(Cart dto : cart) {
			cartIds.add(dto.getCartNo());
		}
		return cartIds;
	}
	
	// 특정 아이디 상품 장바구니 다중 삭제
	@Transactional
	public int deleteAllCart(List<Long> cartNoArr) {
		cartRepository.deleteAllByIdInBatch(cartNoArr);
		return 1;
	}
	
	// 특정 아이디 상품 장바구니 단일 삭제
	public int deleteCart(Long cartNo) {
		cartRepository.deleteById(cartNo);
		return 1;
	}
}






















