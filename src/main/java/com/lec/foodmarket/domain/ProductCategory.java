package com.lec.foodmarket.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="product_category")
public class ProductCategory {
	
	// 기본키 카테고리 번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="category_no")
	private Long categoryNo;
	
	// 카테고리 이름
	@Column(name="category_name")
	private String name;
	
	@OneToMany(mappedBy= "categoryNo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Product> product = new ArrayList<Product>();
}
