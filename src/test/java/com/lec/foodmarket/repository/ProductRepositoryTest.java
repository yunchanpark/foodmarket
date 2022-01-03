package com.lec.foodmarket.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lec.foodmarket.domain.Cart;
import com.lec.foodmarket.domain.Member;
import com.lec.foodmarket.domain.Product;
import com.lec.foodmarket.domain.ProductCategory;

@SpringBootTest
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	
//	@Test
	void crud() {
		System.out.println("\n--TEST#crud()------------------------------------------------");
		ProductCategory category = ProductCategory.builder()
				.name("채소")
				.build();
		
		productCategoryRepository.save(category);
		
		Product product = Product.builder()
				.categoryNo(category)
				.description("명품사과입니다")
				.detailContent("<br><br>")
				.discount(10)
				.imageOrgin("원본파일")
				.imageSave("서버파일")
				.name("박윤찬")
				.price(2000)
				.viewCnt(0)
				.stock(100)
				.purchasePrice(1000)
				.build();
		productRepository.save(product);
		productRepository.findAll().forEach(System.out::println);
		
		
		
		
		System.out.println("---------------------------------------------------------------");
	}
	
	//@Test
	void category() {
		System.out.println(productCategoryRepository.findByName("채소").orElse(null));
	}
	
	@Test
	void cart() {
		List<Member> member = memberRepository.findAll();
		List<Product> product = productRepository.findAll();
		List<Product> product1 = new ArrayList<Product>();
		product1.add(product.get(0));
		product1.add(product.get(1));

		Member m = member.get(0);
		Cart c = Cart.builder()
				.cnt(0)
				.productNo(product.get(0))
				.id(m)
				.build();
		cartRepository.save(c);
		
		Cart c1 = Cart.builder()
				.cnt(0)
				.productNo(product.get(1))
				.id(m)
				.build();
		cartRepository.save(c1);
		
		System.out.println(cartRepository.findAll().get(0));
		List<Cart> cart = cartRepository.findMemberAndProductNoIn(m, product);
		cart.forEach(k -> System.out.println(m));
	}
}



























