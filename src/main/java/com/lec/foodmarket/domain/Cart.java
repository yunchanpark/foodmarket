package com.lec.foodmarket.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="cart")
public class Cart {
	
	// 기본키 카트번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cart_no")
	private Long cartNo;
	
	// 외래키 member의 아이디 
	@ManyToOne
	@NotNull
	@NonNull
	@JoinColumn(name="id")
	private Member id;
	
	// 외래키 product의 상품번호
	@ManyToOne
	@NotNull
	@NonNull
	@JoinColumn(name="product_no")
	private Product productNo;
	
	// 상품수량(Null 허용x)
	@NotNull
	@Column(name="product_cnt", columnDefinition="integer default 1")
	private int cnt;
}
















