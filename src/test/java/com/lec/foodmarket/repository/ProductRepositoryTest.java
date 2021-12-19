package com.lec.foodmarket.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lec.foodmarket.domain.Product;
import com.lec.foodmarket.domain.ProductCategory;

@SpringBootTest
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	
	@Test
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
}
